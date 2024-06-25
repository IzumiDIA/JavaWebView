"use strict";

const Vector3 = {}, Matrix44 = {};
Vector3.create = (x, y, z) => ( { 'x': x, 'y': y, 'z': z } );
Vector3.dot = (v0, v1) => v0.x * v1.x + v0.y * v1.y + v0.z * v1.z;
Vector3.cross = (v, v0, v1) => {
	v.x = v0.y * v1.z - v0.z * v1.y;
	v.y = v0.z * v1.x - v0.x * v1.z;
	v.z = v0.x * v1.y - v0.y * v1.x;
}
Vector3.normalize = v => {
	let l = v.x * v.x + v.y * v.y + v.z * v.z;
	if ( l > 0.00001 ) {
		l = 1.0 / Math.sqrt(l);
		v.x *= l;
		v.y *= l;
		v.z *= l;
	}
};
Vector3.arrayForm = v => {
	if ( v.array ) {
		v.array[0] = v.x;
		v.array[1] = v.y;
		v.array[2] = v.z;
	}else {
		v.array = new Float32Array([v.x, v.y, v.z]);
	}
	return v.array;
};
Matrix44.createIdentity = () => new Float32Array([1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0]);
Matrix44.loadProjection = (m, aspect, v_Deg, near, far) => {
	const h = near * Math.tan(v_Deg * Math.PI / 180.0 * 0.5) * 2.0;
	const w = h * aspect;

	m[0] = 2.0 * near / w;
	m[1] = 0.0;
	m[2] = 0.0;
	m[3] = 0.0;

	m[4] = 0.0;
	m[5] = 2.0 * near / h;
	m[6] = 0.0;
	m[7] = 0.0;

	m[8] = 0.0;
	m[9] = 0.0;
	m[10] = -( far + near ) / ( far - near );
	m[11] = -1.0;

	m[12] = 0.0;
	m[13] = 0.0;
	m[14] = -2.0 * far * near / ( far - near );
	m[15] = 0.0;
};
Matrix44.loadLookAt = (m, v_Pos, v_Look, vup) => {
	const frontV = Vector3.create(v_Pos.x - v_Look.x, v_Pos.y - v_Look.y, v_Pos.z - v_Look.z);
	Vector3.normalize(frontV);
	const sideV = Vector3.create(1.0, 0.0, 0.0);
	Vector3.cross(sideV, vup, frontV);
	Vector3.normalize(sideV);
	const topV = Vector3.create(1.0, 0.0, 0.0);
	Vector3.cross(topV, frontV, sideV);
	Vector3.normalize(topV);

	m[0] = sideV.x;
	m[1] = topV.x;
	m[2] = frontV.x;
	m[3] = 0.0;

	m[4] = sideV.y;
	m[5] = topV.y;
	m[6] = frontV.y;
	m[7] = 0.0;

	m[8] = sideV.z;
	m[9] = topV.z;
	m[10] = frontV.z;
	m[11] = 0.0;

	m[12] = -( v_Pos.x * m[0] + v_Pos.y * m[4] + v_Pos.z * m[8] );
	m[13] = -( v_Pos.x * m[1] + v_Pos.y * m[5] + v_Pos.z * m[9] );
	m[14] = -( v_Pos.x * m[2] + v_Pos.y * m[6] + v_Pos.z * m[10] );
	m[15] = 1.0;
}

//
let timeInfo = {
	start: 0, prev: 0, // Date
	delta: 0, elapsed: 0 // Number(sec)
}, gl;
const renderSpec = {
	width: 0,
	height: 0,
	aspect: 1,
	array: new Float32Array(3),
	halfWidth: 0,
	halfHeight: 0,
	halfArray: new Float32Array(3)
	// and some render targets. see setViewport()
}
renderSpec.setSize = (w, h) => {
	renderSpec.width = w;
	renderSpec.height = h;
	renderSpec.aspect = renderSpec.width / renderSpec.height;
	renderSpec.array[0] = renderSpec.width;
	renderSpec.array[1] = renderSpec.height;
	renderSpec.array[2] = renderSpec.aspect;

	renderSpec.halfWidth = Math.floor(w / 2);
	renderSpec.halfHeight = Math.floor(h / 2);
	renderSpec.halfArray[0] = renderSpec.halfWidth;
	renderSpec.halfArray[1] = renderSpec.halfHeight;
	renderSpec.halfArray[2] = renderSpec.halfWidth / renderSpec.halfHeight;
};

function deleteRenderTarget(rt) {
	gl.deleteFramebuffer(rt.frameBuffer);
	gl.deleteRenderbuffer(rt.renderBuffer);
	gl.deleteTexture(rt.texture);
}

function createRenderTarget(w, h) {
	const ret = {
		width: w,
		height: h,
		sizeArray: new Float32Array([w, h, w / h]),
		dtxArray: new Float32Array([1.0 / w, 1.0 / h])
	}
	ret.frameBuffer = gl.createFramebuffer();
	ret.renderBuffer = gl.createRenderbuffer();
	ret.texture = gl.createTexture();

	gl.bindTexture(gl.TEXTURE_2D, ret.texture);
	gl.texImage2D(gl.TEXTURE_2D, 0, gl.RGBA, w, h, 0, gl.RGBA, gl.UNSIGNED_BYTE, null);
	gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.CLAMP_TO_EDGE);
	gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.CLAMP_TO_EDGE);
	gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.LINEAR);
	gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR);

	gl.bindFramebuffer(gl.FRAMEBUFFER, ret.frameBuffer);
	gl.framebufferTexture2D(gl.FRAMEBUFFER, gl.COLOR_ATTACHMENT0, gl.TEXTURE_2D, ret.texture, 0);

	gl.bindRenderbuffer(gl.RENDERBUFFER, ret.renderBuffer);
	gl.renderbufferStorage(gl.RENDERBUFFER, gl.DEPTH_COMPONENT16, w, h);
	gl.framebufferRenderbuffer(gl.FRAMEBUFFER, gl.DEPTH_ATTACHMENT, gl.RENDERBUFFER, ret.renderBuffer);

	gl.bindTexture(gl.TEXTURE_2D, null);
	gl.bindRenderbuffer(gl.RENDERBUFFER, null);
	gl.bindFramebuffer(gl.FRAMEBUFFER, null);

	return ret;
}

function compileShader(shType, shSrc) {
	const retSh = gl.createShader(shType);

	gl.shaderSource(retSh, shSrc);
	gl.compileShader(retSh);

	if ( !gl.getShaderParameter(retSh, gl.COMPILE_STATUS) ) {
		const errLog = gl.getShaderInfoLog(retSh);
		gl.deleteShader(retSh);
		console.error(errLog);
		return null;
	}
	return retSh;
}

function createShader(vtxSrc, frgSrc, uniformList, attrList) {
	const vsh = compileShader(gl.VERTEX_SHADER, vtxSrc), fsh = compileShader(gl.FRAGMENT_SHADER, frgSrc);

	if ( !( vsh == null || fsh == null ) ) {
		const program = gl.createProgram();
		gl.attachShader(program, vsh);
		gl.attachShader(program, fsh);
		gl.deleteShader(vsh);
		gl.deleteShader(fsh);
		gl.linkProgram(program);
		if ( !gl.getProgramParameter(program, gl.LINK_STATUS) ) {
			const errLog = gl.getProgramInfoLog(program);
			console.error(errLog);
			return null;
		}
		if ( uniformList ) {
			program.uniforms = {};
			for ( const element of uniformList ) program.uniforms[element] = gl.getUniformLocation(program, element);
		}
		if ( attrList ) {
			program.attributes = {};
			for ( const element of attrList ) {
				const attr = element;
				program.attributes[attr] = gl.getAttribLocation(program, attr);
			}
		}
		return program;
	}else {
		return null;
	}
}

function useShader(program) {
	gl.useProgram(program);
	for ( const attr in program.attributes ) gl.enableVertexAttribArray(program.attributes[attr]);
}

function unuseShader(program) {
	for ( const attr in program.attributes ) {
		gl.disableVertexAttribArray(program.attributes[attr]);
	}
	gl.useProgram(null);
}

/////
const projection = {
	angle: 60,
	nearFar: new Float32Array([0.1, 100.0]),
	matrix: Matrix44.createIdentity()
}
const camera = {
	position: Vector3.create(0, 0, 100),
	lookAt: Vector3.create(0, 0, 0),
	up: Vector3.create(0, 1, 0),
	dof: Vector3.create(10.0, 4.0, 8.0),
	matrix: Matrix44.createIdentity()
}
const pointFlower = {};
let sceneStandBy = false;

function BlossomParticle() {
	this.velocity = new Array(3);
	this.rotation = new Array(3);
	this.position = new Array(3);
	this.euler = new Array(3);
	this.size = 1.0;
	this.alpha = 1.0;
	this.zkey = 0.0;
}

BlossomParticle.prototype.setVelocity = function (vx, vy, vz) {
	this.velocity[0] = vx;
	this.velocity[1] = vy;
	this.velocity[2] = vz;
}

BlossomParticle.prototype.setRotation = function (rx, ry, rz) {
	this.rotation[0] = rx;
	this.rotation[1] = ry;
	this.rotation[2] = rz;
}

BlossomParticle.prototype.setPosition = function (nx, ny, nz) {
	this.position[0] = nx;
	this.position[1] = ny;
	this.position[2] = nz;
}

BlossomParticle.prototype.setEulerAngles = function (rx, ry, rz) {
	this.euler[0] = rx;
	this.euler[1] = ry;
	this.euler[2] = rz;
}

BlossomParticle.prototype.setSize = function (s) {
	this.size = s;
}

BlossomParticle.prototype.update = function (dt) {
	this.position[0] += this.velocity[0] * dt;
	this.position[1] += this.velocity[1] * dt;
	this.position[2] += this.velocity[2] * dt;

	this.euler[0] += this.rotation[0] * dt;
	this.euler[1] += this.rotation[1] * dt;
	this.euler[2] += this.rotation[2] * dt;
}

function createPointFlowers() {
	// get point sizes
	const prm = gl.getParameter(gl.ALIASED_POINT_SIZE_RANGE);
	renderSpec.pointSize = { min: prm[0], max: prm[1] };

	const vtxSrc = document.getElementById("sakura_point_vsh").textContent,
		frgSrc = document.getElementById("sakura_point_fsh").textContent;

	pointFlower.program = createShader(
		vtxSrc, frgSrc,
		['uProjection', 'uModelView', 'uResolution', 'uOffset', 'uDOF', 'uFade'],
		['aPosition', 'aEuler', 'aMisc']
	);

	useShader(pointFlower.program);
	pointFlower.offset = new Float32Array([0.0, 0.0, 0.0]);
	pointFlower.fader = Vector3.create(0.0, 10.0, 0.0);

	// paramerters: velocity[3], rotate[3]
	pointFlower.numFlowers = 1600;
	pointFlower.particles = new Array(pointFlower.numFlowers);
	// vertex attributes {position[3], euler_xyz[3], size[1]}
	pointFlower.dataArray = new Float32Array(pointFlower.numFlowers * ( 3 + 3 + 2 ));
	pointFlower.positionArrayOffset = 0;
	pointFlower.eulerArrayOffset = pointFlower.numFlowers * 3;
	pointFlower.miscArrayOffset = pointFlower.numFlowers * 6;

	pointFlower.buffer = gl.createBuffer();
	gl.bindBuffer(gl.ARRAY_BUFFER, pointFlower.buffer);
	gl.bufferData(gl.ARRAY_BUFFER, pointFlower.dataArray, gl.DYNAMIC_DRAW);
	gl.bindBuffer(gl.ARRAY_BUFFER, null);

	unuseShader(pointFlower.program);

	for ( let i = 0; i < pointFlower.numFlowers; i++ ) pointFlower.particles[i] = new BlossomParticle();
}

function initPointFlowers() {
	//area
	pointFlower.area = Vector3.create(20.0, 20.0, 20.0);
	pointFlower.area.x = pointFlower.area.y * renderSpec.aspect;

	pointFlower.fader.x = 10.0; //env fade start
	pointFlower.fader.y = pointFlower.area.z; //env fade half
	pointFlower.fader.z = 0.1;  //near fade start

	//particles
	const PI2 = Math.PI * 2.0, tempV3 = Vector3.create(0, 0, 0), symmetryrand = () => ( Math.random() * 2.0 - 1.0 );
	let tempV = 0;

	for ( let i = 0; i < pointFlower.numFlowers; i++ ) {
		const tmpPrtcl = pointFlower.particles[i];

		//velocity
		tempV3.x = symmetryrand() * 0.3 + 0.8;
		tempV3.y = symmetryrand() * 0.2 - 1.0;
		tempV3.z = symmetryrand() * 0.3 + 0.5;
		Vector3.normalize(tempV3);
		tempV = 2.0 + Math.random();
		tmpPrtcl.setVelocity(tempV3.x * tempV, tempV3.y * tempV, tempV3.z * tempV);

		//rotation
		tmpPrtcl.setRotation(
			symmetryrand() * PI2 * 0.5,
			symmetryrand() * PI2 * 0.5,
			symmetryrand() * PI2 * 0.5
		);

		//position
		tmpPrtcl.setPosition(
			symmetryrand() * pointFlower.area.x,
			symmetryrand() * pointFlower.area.y,
			symmetryrand() * pointFlower.area.z
		);

		//euler
		tmpPrtcl.setEulerAngles(
			Math.random() * Math.PI * 2.0,
			Math.random() * Math.PI * 2.0,
			Math.random() * Math.PI * 2.0
		);

		//size
		tmpPrtcl.setSize(0.9 + Math.random() * 0.1);
	}
}

function renderPointFlowers() {
	let prtcl, i;
//update
	const PI2 = Math.PI * 2.0, repeatPos = (prt, cmp, limit) => {

		//out of area
		if ( Math.abs(prt.position[cmp]) - prt.size * 0.5 > limit ) {
			if ( prt.position[cmp] > 0 ) {
				prt.position[cmp] -= limit * 2.0;
			}else {
				prt.position[cmp] += limit * 2.0;
			}
		}
	}, repeatEuler = (prt, cmp) => {
		prt.euler[cmp] = prt.euler[cmp] % PI2;
		if ( prt.euler[cmp] < 0.0 ) {
			prt.euler[cmp] += PI2;
		}
	}

	for ( i = 0; i < pointFlower.numFlowers; i++ ) {
		prtcl = pointFlower.particles[i];
		prtcl.update(timeInfo.delta, timeInfo.elapsed);
		repeatPos(prtcl, 0, pointFlower.area.x);
		repeatPos(prtcl, 1, pointFlower.area.y);
		repeatPos(prtcl, 2, pointFlower.area.z);
		repeatEuler(prtcl, 0);
		repeatEuler(prtcl, 1);
		repeatEuler(prtcl, 2);

		prtcl.alpha = 1.0;

		prtcl.zkey = ( camera.matrix[2] * prtcl.position[0]
			+ camera.matrix[6] * prtcl.position[1]
			+ camera.matrix[10] * prtcl.position[2]
			+ camera.matrix[14] );
	}

	// sort
	pointFlower.particles.sort((p0, p1) => p0.zkey - p1.zkey);

	// update data
	let iPos = pointFlower.positionArrayOffset, iEuler = pointFlower.eulerArrayOffset,
		iMisc = pointFlower.miscArrayOffset;
	for ( i = 0; i < pointFlower.numFlowers; i++ ) {
		prtcl = pointFlower.particles[i];
		pointFlower.dataArray[iPos] = prtcl.position[0];
		pointFlower.dataArray[iPos + 1] = prtcl.position[1];
		pointFlower.dataArray[iPos + 2] = prtcl.position[2];
		iPos += 3;
		pointFlower.dataArray[iEuler] = prtcl.euler[0];
		pointFlower.dataArray[iEuler + 1] = prtcl.euler[1];
		pointFlower.dataArray[iEuler + 2] = prtcl.euler[2];
		iEuler += 3;
		pointFlower.dataArray[iMisc] = prtcl.size;
		pointFlower.dataArray[iMisc + 1] = prtcl.alpha;
		iMisc += 2;
	}

	//draw
	const {
		DYNAMIC_DRAW,
		ONE_MINUS_SRC_ALPHA,
		DEPTH_TEST,
		FLOAT,
		SRC_ALPHA,
		BLEND,
		ARRAY_BUFFER,
		POINT
	} = gl;
	gl.enable(BLEND);
	gl.blendFunc(SRC_ALPHA, ONE_MINUS_SRC_ALPHA);

	const pRog = pointFlower.program;
	useShader(pRog);
	const { uFade, uOffset, uProjection, uDOF, uModelView: uModelView, uResolution } = pRog.uniforms;
	gl.uniformMatrix4fv(uProjection, false, projection.matrix);
	gl.uniformMatrix4fv(uModelView, false, camera.matrix);
	gl.uniform3fv(uResolution, renderSpec.array);
	gl.uniform3fv(uDOF, Vector3.arrayForm(camera.dof));
	gl.uniform3fv(uFade, Vector3.arrayForm(pointFlower.fader));

	gl.bindBuffer(ARRAY_BUFFER, pointFlower.buffer);
	gl.bufferData(ARRAY_BUFFER, pointFlower.dataArray, DYNAMIC_DRAW);
	const { aMisc, aPosition, aEuler } = pRog.attributes;
	gl.vertexAttribPointer(aPosition, 3, FLOAT, false, 0, pointFlower.positionArrayOffset * Float32Array.BYTES_PER_ELEMENT);
	gl.vertexAttribPointer(aEuler, 3, FLOAT, false, 0, pointFlower.eulerArrayOffset * Float32Array.BYTES_PER_ELEMENT);
	gl.vertexAttribPointer(aMisc, 2, FLOAT, false, 0, pointFlower.miscArrayOffset * Float32Array.BYTES_PER_ELEMENT);

	// doubler
	for ( i = 1; i < 2; i++ ) {
		const zPos = i * -2.0;
		pointFlower.offset[0] = pointFlower.area.x * -1.0;
		pointFlower.offset[1] = pointFlower.area.y * -1.0;
		pointFlower.offset[2] = pointFlower.area.z * zPos;
		gl.uniform3fv(uOffset, pointFlower.offset);
		gl.drawArrays(POINT, 0, pointFlower.numFlowers);

		pointFlower.offset[0] = pointFlower.area.x * -1.0;
		pointFlower.offset[1] = pointFlower.area.y * 1.0;
		pointFlower.offset[2] = pointFlower.area.z * zPos;
		gl.uniform3fv(uOffset, pointFlower.offset);
		gl.drawArrays(POINT, 0, pointFlower.numFlowers);

		pointFlower.offset[0] = pointFlower.area.x * 1.0;
		pointFlower.offset[1] = pointFlower.area.y * -1.0;
		pointFlower.offset[2] = pointFlower.area.z * zPos;
		gl.uniform3fv(uOffset, pointFlower.offset);
		gl.drawArrays(POINT, 0, pointFlower.numFlowers);

		pointFlower.offset[0] = pointFlower.area.x * 1.0;
		pointFlower.offset[1] = pointFlower.area.y * 1.0;
		pointFlower.offset[2] = pointFlower.area.z * zPos;
		gl.uniform3fv(uOffset, pointFlower.offset);
		gl.drawArrays(POINT, 0, pointFlower.numFlowers);
	}

	//main
	pointFlower.offset[0] = 0.0;
	pointFlower.offset[1] = 0.0;
	pointFlower.offset[2] = 0.0;
	gl.uniform3fv(uOffset, pointFlower.offset);
	gl.drawArrays(POINT, 0, pointFlower.numFlowers);

	gl.bindBuffer(ARRAY_BUFFER, null);
	unuseShader(pRog);

	gl.enable(DEPTH_TEST);
	gl.disable(BLEND);
}

// effects
//common util
function createEffectProgram(vtxSrc, frgSrc, exUniforms, exAttrs) {
	const ret = {};
	let uniforms = ['uResolution', 'uSrc', 'uDelta'];
	if ( exUniforms ) uniforms = uniforms.concat(exUniforms);
	let attrs = ['aPosition'];
	if ( exAttrs ) attrs = attrs.concat(exAttrs);

	ret.program = createShader(vtxSrc, frgSrc, uniforms, attrs);
	useShader(ret.program);

	ret.dataArray = new Float32Array([
		-1.0, -1.0,
		1.0, -1.0,
		-1.0, 1.0,
		1.0, 1.0
	]);
	ret.buffer = gl.createBuffer();
	gl.bindBuffer(gl.ARRAY_BUFFER, ret.buffer);
	gl.bufferData(gl.ARRAY_BUFFER, ret.dataArray, gl.STATIC_DRAW);

	gl.bindBuffer(gl.ARRAY_BUFFER, null);
	unuseShader(ret.program);

	return ret;
}

// basic usage
// useEffect(prog, srctex({'texture':texid, 'dtxArray':(f32)[dtx, dty]})); //basic initialize
// gl.uniform**(...); //additional uniforms
// drawEffect()
// unuseEffect(prog)
// TEXTURE0 makes src
function useEffect(fxObj, srcTex) {
	const pRog = fxObj.program;
	useShader(pRog);
	const { uResolution, uDelta, uSrc } = pRog.uniforms;
	gl.uniform3fv(uResolution, renderSpec.array);

	if ( srcTex ) {
		gl.uniform2fv(uDelta, srcTex.dtxArray);
		gl.uniform1i(uSrc, 0);

		gl.activeTexture(gl.TEXTURE0);
		gl.bindTexture(gl.TEXTURE_2D, srcTex.texture);
	}
}

function drawEffect(fxObj) {
	gl.bindBuffer(gl.ARRAY_BUFFER, fxObj.buffer);
	gl.vertexAttribPointer(fxObj.program.attributes["aPosition"], 2, gl.FLOAT, false, 0, 0);
	gl.drawArrays(gl.TRIANGLE_STRIP, 0, 4);
}

const effectLib = {};

function createEffectLib() {
	//common
	const cmnVtxSrc = document.getElementById("fx_common_vsh").textContent;

	//background
	let frgSrc = document.getElementById("bg_fsh").textContent;
	effectLib.sceneBg = createEffectProgram(cmnVtxSrc, frgSrc, ['uTimes'], null);

	// make brightpixels buffer
	frgSrc = document.getElementById("fx_brightbuf_fsh").textContent;
	effectLib.mkBrightBuf = createEffectProgram(cmnVtxSrc, frgSrc, null, null);

	// direction blur
	frgSrc = document.getElementById("fx_dirblur_r4_fsh").textContent;
	effectLib.dirBlur = createEffectProgram(cmnVtxSrc, frgSrc, ['uBlurDir'], null);

	//final composite
	let vtxSrc = document.getElementById("pp_final_vsh").textContent;
	frgSrc = document.getElementById("pp_final_fsh").textContent;
	effectLib.finalComp = createEffectProgram(vtxSrc, frgSrc, ['uBloom'], null);
}

function renderBackground() {
	gl.disable(gl.DEPTH_TEST);

	useEffect(effectLib.sceneBg, null);
	gl.uniform2f(effectLib.sceneBg.program.uniforms["uTimes"], timeInfo.elapsed, timeInfo.delta);
	drawEffect(effectLib.sceneBg);
	unuseShader(effectLib.sceneBg.program);

	gl.enable(gl.DEPTH_TEST);
}

// post process
function renderPostProcess() {
	// gl.enable(gl.TEXTURE_2D);
	gl.disable(gl.DEPTH_TEST);
	const bindRT = (rt, isClear) => {
		gl.bindFramebuffer(gl.FRAMEBUFFER, rt.frameBuffer);
		gl.viewport(0, 0, rt.width, rt.height);
		if ( isClear ) {
			gl.clearColor(0, 0, 0, 0);
			gl.clear(gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT);
		}
	}

	//make bright buff
	const { wHalfRT1, wHalfRT0, mainRT, height, width } = renderSpec;
	bindRT(wHalfRT0, true);
	useEffect(effectLib.mkBrightBuf, mainRT);
	drawEffect(effectLib.mkBrightBuf);
	unuseShader(effectLib.mkBrightBuf.program);

	// make bloom
	for ( let i = 0; i < 2; ++i ) {
		const p = 1.5 + i, s = 2.0 + i;
		bindRT(wHalfRT1, true);
		useEffect(effectLib.dirBlur, wHalfRT0);
		const { uBlurDir } = effectLib.dirBlur.program.uniforms;
		gl.uniform4f(uBlurDir, p, 0.0, s, 0.0);
		drawEffect(effectLib.dirBlur);
		unuseShader(effectLib.dirBlur.program);

		bindRT(wHalfRT0, true);
		useEffect(effectLib.dirBlur, wHalfRT1);
		gl.uniform4f(uBlurDir, 0.0, p, 0.0, s);
		drawEffect(effectLib.dirBlur);
		unuseShader(effectLib.dirBlur.program);
	}

	//display
	gl.bindFramebuffer(gl.FRAMEBUFFER, null);
	gl.viewport(0, 0, width, height);
	gl.clear(gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT);

	useEffect(effectLib.finalComp, mainRT);
	gl.uniform1i(effectLib.finalComp.program.uniforms["uBloom"], 1);
	gl.activeTexture(gl.TEXTURE1);
	gl.bindTexture(gl.TEXTURE_2D, wHalfRT0.texture);
	drawEffect(effectLib.finalComp);
	unuseShader(effectLib.finalComp.program);

	gl.enable(gl.DEPTH_TEST);
}


function createScene() {
	createEffectLib();
	createPointFlowers();
	sceneStandBy = true;
}

function initScene() {
	initPointFlowers();
	camera.position.z = pointFlower.area.z + projection.nearFar[0];
	projection.angle = Math.atan2(pointFlower.area.y, camera.position.z + pointFlower.area.z) * 180.0 / Math.PI * 2.0;
	Matrix44.loadProjection(projection.matrix, renderSpec.aspect, projection.angle, projection.nearFar[0], projection.nearFar[1]);
}

function renderScene() {
	//draw
	Matrix44.loadLookAt(camera.matrix, camera.position, camera.lookAt, camera.up);

	gl.enable(gl.DEPTH_TEST);

	const { frameBuffer, height, width } = renderSpec["mainRT"];
	gl.bindFramebuffer(gl.FRAMEBUFFER, frameBuffer);
	gl.viewport(0, 0, width, height);
	gl.clearColor(0.005, 0, 0.05, 0);
	gl.clear(gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT);

	renderBackground();
	renderPointFlowers();
	renderPostProcess();
}

function onResize() {
	makeCanvasFullScreen(document.getElementById("sakura"));
	setViewports();
	if ( sceneStandBy ) initScene();
}

function setViewports() {
	renderSpec.setSize(gl.canvas.width, gl.canvas.height);

	gl.clearColor(0.2, 0.2, 0.5, 1.0);
	gl.viewport(0, 0, renderSpec.width, renderSpec.height);

	const rtFunction = (rtName, rtw, rth) => {
		const rt = renderSpec[rtName];
		if ( rt ) deleteRenderTarget(rt);
		renderSpec[rtName] = createRenderTarget(rtw, rth);
	};
	rtFunction('mainRT', renderSpec.width, renderSpec.height);
	rtFunction('wFullRT0', renderSpec.width, renderSpec.height);
	rtFunction('wFullRT1', renderSpec.width, renderSpec.height);
	rtFunction('wHalfRT0', renderSpec.halfWidth, renderSpec.halfHeight);
	rtFunction('wHalfRT1', renderSpec.halfWidth, renderSpec.halfHeight);
}

const animating = true;

function animate() {
	const curDate = new Date();
	timeInfo.elapsed = ( curDate - timeInfo.start ) / 1000.0;
	timeInfo.delta = ( curDate - timeInfo.prev ) / 1000.0;
	timeInfo.prev = curDate;

	if ( animating ) requestAnimationFrame(animate);
	renderScene();
}

function makeCanvasFullScreen(canvas) {
	const b = document.body, d = document.documentElement,
		fullH = Math.max(b.clientHeight, b.scrollHeight, d.scrollHeight, d.clientHeight);
	canvas.width = Math.max(b.clientWidth, b.scrollWidth, d.scrollWidth, d.clientWidth);
	canvas.height = fullH;
}

window.addEventListener('load', () => {
	const canvas = document.getElementById("sakura");
	try {
		makeCanvasFullScreen(canvas);
		gl = canvas.getContext('webgl');
	}catch ( e ) {
		alert("WebGL not supported." + e);
		console.error(e);
		return;
	}

	window.addEventListener('resize', onResize);

	setViewports();
	createScene();
	initScene();

	timeInfo.start = new Date();
	timeInfo.prev = timeInfo.start;
	animate();
});

//set window.requestAnimationFrame
( (w, r) => {
	w['r' + r] = w['r' + r] || w['webkitR' + r] || w['mozR' + r] || w['msR' + r] || w['oR' + r] || function (c) {
		w.setTimeout(c, 100 / 6);
	};
} )(window, 'requestAnimationFrame');
