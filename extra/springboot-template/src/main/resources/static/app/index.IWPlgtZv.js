(function(){const t=document.createElement("link").relList;if(t&&t.supports&&t.supports("modulepreload"))return;for(const i of document.querySelectorAll('link[rel="modulepreload"]'))r(i);new MutationObserver(i=>{for(const o of i)if(o.type==="childList")for(const s of o.addedNodes)s.tagName==="LINK"&&s.rel==="modulepreload"&&r(s)}).observe(document,{childList:!0,subtree:!0});function e(i){const o={};return i.integrity&&(o.integrity=i.integrity),i.referrerPolicy&&(o.referrerPolicy=i.referrerPolicy),i.crossOrigin==="use-credentials"?o.credentials="include":i.crossOrigin==="anonymous"?o.credentials="omit":o.credentials="same-origin",o}function r(i){if(i.ep)return;i.ep=!0;const o=e(i);fetch(i.href,o)}})();var qa={},Qa;function wh(){if(Qa)return qa;Qa=1;var n=Object.defineProperty,t=Object.defineProperties,e=Object.getOwnPropertyDescriptors,r=Object.getOwnPropertySymbols,i=Object.prototype.hasOwnProperty,o=Object.prototype.propertyIsEnumerable,s=(h,d,D)=>d in h?n(h,d,{enumerable:!0,configurable:!0,writable:!0,value:D}):h[d]=D,a=(h,d)=>{for(var D in d||(d={}))i.call(d,D)&&s(h,D,d[D]);if(r)for(var D of r(d))o.call(d,D)&&s(h,D,d[D]);return h},l=(h,d)=>t(h,e(d)),u=(h,d,D)=>(s(h,typeof d!="symbol"?d+"":d,D),D),c=globalThis;function f(h){return(c.__Zone_symbol_prefix||"__zone_symbol__")+h}function b(){const h=c.performance;function d(yn){h&&h.mark&&h.mark(yn)}function D(yn,v){h&&h.measure&&h.measure(yn,v)}d("Zone");const m=class _o{constructor(v,y){u(this,"_parent"),u(this,"_name"),u(this,"_properties"),u(this,"_zoneDelegate"),this._parent=v,this._name=y?y.name||"unnamed":"<root>",this._properties=y&&y.properties||{},this._zoneDelegate=new H(this,this._parent&&this._parent._zoneDelegate,y)}static assertZonePatched(){if(c.Promise!==W.ZoneAwarePromise)throw new Error("Zone.js has detected that ZoneAwarePromise `(window|global).Promise` has been overwritten.\nMost likely cause is that a Promise polyfill has been loaded after Zone.js (Polyfilling Promise api is not necessary when zone.js is loaded. If you must load one, do so before loading zone.js.)")}static get root(){let v=_o.current;for(;v.parent;)v=v.parent;return v}static get current(){return U.zone}static get currentTask(){return Yn}static __load_patch(v,y,g=!1){if(W.hasOwnProperty(v)){const j=c[f("forceDuplicateZoneCheck")]===!0;if(!g&&j)throw Error("Already loaded patch: "+v)}else if(!c["__Zone_disable_"+v]){const j="Zone:"+v;d(j),W[v]=y(c,_o,tn),D(j,j)}}get parent(){return this._parent}get name(){return this._name}get(v){const y=this.getZoneWith(v);if(y)return y._properties[v]}getZoneWith(v){let y=this;for(;y;){if(y._properties.hasOwnProperty(v))return y;y=y._parent}return null}fork(v){if(!v)throw new Error("ZoneSpec required!");return this._zoneDelegate.fork(this,v)}wrap(v,y){if(typeof v!="function")throw new Error("Expecting function got: "+v);const g=this._zoneDelegate.intercept(this,v,y),j=this;return function(){return j.runGuarded(g,this,arguments,y)}}run(v,y,g,j){U={parent:U,zone:this};try{return this._zoneDelegate.invoke(this,v,y,g,j)}finally{U=U.parent}}runGuarded(v,y=null,g,j){U={parent:U,zone:this};try{try{return this._zoneDelegate.invoke(this,v,y,g,j)}catch(vn){if(this._zoneDelegate.handleError(this,vn))throw vn}}finally{U=U.parent}}runTask(v,y,g){if(v.zone!=this)throw new Error("A task can only be run in the zone of creation! (Creation: "+(v.zone||Un).name+"; Execution: "+this.name+")");const j=v,{type:vn,data:{isPeriodic:en=!1,isRefreshable:ht=!1}={}}=v;if(v.state===N&&(vn===Q||vn===On))return;const Lt=v.state!=pn;Lt&&j._transitionTo(pn,wn);const Nt=Yn;Yn=j,U={parent:U,zone:this};try{vn==On&&v.data&&!en&&!ht&&(v.cancelFn=void 0);try{return this._zoneDelegate.invokeTask(this,j,y,g)}catch(pt){if(this._zoneDelegate.handleError(this,pt))throw pt}}finally{const pt=v.state;if(pt!==N&&pt!==bn)if(vn==Q||en||ht&&pt===I)Lt&&j._transitionTo(wn,pn,I);else{const x=j._zoneDelegates;this._updateTaskCount(j,-1),Lt&&j._transitionTo(N,pn,N),ht&&(j._zoneDelegates=x)}U=U.parent,Yn=Nt}}scheduleTask(v){if(v.zone&&v.zone!==this){let g=this;for(;g;){if(g===v.zone)throw Error(`can not reschedule task to ${this.name} which is descendants of the original zone ${v.zone.name}`);g=g.parent}}v._transitionTo(I,N);const y=[];v._zoneDelegates=y,v._zone=this;try{v=this._zoneDelegate.scheduleTask(this,v)}catch(g){throw v._transitionTo(bn,I,N),this._zoneDelegate.handleError(this,g),g}return v._zoneDelegates===y&&this._updateTaskCount(v,1),v.state==I&&v._transitionTo(wn,I),v}scheduleMicroTask(v,y,g,j){return this.scheduleTask(new R(M,v,y,g,j,void 0))}scheduleMacroTask(v,y,g,j,vn){return this.scheduleTask(new R(On,v,y,g,j,vn))}scheduleEventTask(v,y,g,j,vn){return this.scheduleTask(new R(Q,v,y,g,j,vn))}cancelTask(v){if(v.zone!=this)throw new Error("A task can only be cancelled in the zone of creation! (Creation: "+(v.zone||Un).name+"; Execution: "+this.name+")");if(!(v.state!==wn&&v.state!==pn)){v._transitionTo(Sn,wn,pn);try{this._zoneDelegate.cancelTask(this,v)}catch(y){throw v._transitionTo(bn,Sn),this._zoneDelegate.handleError(this,y),y}return this._updateTaskCount(v,-1),v._transitionTo(N,Sn),v.runCount=-1,v}}_updateTaskCount(v,y){const g=v._zoneDelegates;y==-1&&(v._zoneDelegates=null);for(let j=0;j<g.length;j++)g[j]._updateTaskCount(v.type,y)}};u(m,"__symbol__",f);let _=m;const k={name:"",onHasTask:(yn,v,y,g)=>yn.hasTask(y,g),onScheduleTask:(yn,v,y,g)=>yn.scheduleTask(y,g),onInvokeTask:(yn,v,y,g,j,vn)=>yn.invokeTask(y,g,j,vn),onCancelTask:(yn,v,y,g)=>yn.cancelTask(y,g)};class H{constructor(v,y,g){u(this,"_zone"),u(this,"_taskCounts",{microTask:0,macroTask:0,eventTask:0}),u(this,"_parentDelegate"),u(this,"_forkDlgt"),u(this,"_forkZS"),u(this,"_forkCurrZone"),u(this,"_interceptDlgt"),u(this,"_interceptZS"),u(this,"_interceptCurrZone"),u(this,"_invokeDlgt"),u(this,"_invokeZS"),u(this,"_invokeCurrZone"),u(this,"_handleErrorDlgt"),u(this,"_handleErrorZS"),u(this,"_handleErrorCurrZone"),u(this,"_scheduleTaskDlgt"),u(this,"_scheduleTaskZS"),u(this,"_scheduleTaskCurrZone"),u(this,"_invokeTaskDlgt"),u(this,"_invokeTaskZS"),u(this,"_invokeTaskCurrZone"),u(this,"_cancelTaskDlgt"),u(this,"_cancelTaskZS"),u(this,"_cancelTaskCurrZone"),u(this,"_hasTaskDlgt"),u(this,"_hasTaskDlgtOwner"),u(this,"_hasTaskZS"),u(this,"_hasTaskCurrZone"),this._zone=v,this._parentDelegate=y,this._forkZS=g&&(g&&g.onFork?g:y._forkZS),this._forkDlgt=g&&(g.onFork?y:y._forkDlgt),this._forkCurrZone=g&&(g.onFork?this._zone:y._forkCurrZone),this._interceptZS=g&&(g.onIntercept?g:y._interceptZS),this._interceptDlgt=g&&(g.onIntercept?y:y._interceptDlgt),this._interceptCurrZone=g&&(g.onIntercept?this._zone:y._interceptCurrZone),this._invokeZS=g&&(g.onInvoke?g:y._invokeZS),this._invokeDlgt=g&&(g.onInvoke?y:y._invokeDlgt),this._invokeCurrZone=g&&(g.onInvoke?this._zone:y._invokeCurrZone),this._handleErrorZS=g&&(g.onHandleError?g:y._handleErrorZS),this._handleErrorDlgt=g&&(g.onHandleError?y:y._handleErrorDlgt),this._handleErrorCurrZone=g&&(g.onHandleError?this._zone:y._handleErrorCurrZone),this._scheduleTaskZS=g&&(g.onScheduleTask?g:y._scheduleTaskZS),this._scheduleTaskDlgt=g&&(g.onScheduleTask?y:y._scheduleTaskDlgt),this._scheduleTaskCurrZone=g&&(g.onScheduleTask?this._zone:y._scheduleTaskCurrZone),this._invokeTaskZS=g&&(g.onInvokeTask?g:y._invokeTaskZS),this._invokeTaskDlgt=g&&(g.onInvokeTask?y:y._invokeTaskDlgt),this._invokeTaskCurrZone=g&&(g.onInvokeTask?this._zone:y._invokeTaskCurrZone),this._cancelTaskZS=g&&(g.onCancelTask?g:y._cancelTaskZS),this._cancelTaskDlgt=g&&(g.onCancelTask?y:y._cancelTaskDlgt),this._cancelTaskCurrZone=g&&(g.onCancelTask?this._zone:y._cancelTaskCurrZone),this._hasTaskZS=null,this._hasTaskDlgt=null,this._hasTaskDlgtOwner=null,this._hasTaskCurrZone=null;const j=g&&g.onHasTask,vn=y&&y._hasTaskZS;(j||vn)&&(this._hasTaskZS=j?g:k,this._hasTaskDlgt=y,this._hasTaskDlgtOwner=this,this._hasTaskCurrZone=this._zone,g.onScheduleTask||(this._scheduleTaskZS=k,this._scheduleTaskDlgt=y,this._scheduleTaskCurrZone=this._zone),g.onInvokeTask||(this._invokeTaskZS=k,this._invokeTaskDlgt=y,this._invokeTaskCurrZone=this._zone),g.onCancelTask||(this._cancelTaskZS=k,this._cancelTaskDlgt=y,this._cancelTaskCurrZone=this._zone))}get zone(){return this._zone}fork(v,y){return this._forkZS?this._forkZS.onFork(this._forkDlgt,this.zone,v,y):new _(v,y)}intercept(v,y,g){return this._interceptZS?this._interceptZS.onIntercept(this._interceptDlgt,this._interceptCurrZone,v,y,g):y}invoke(v,y,g,j,vn){return this._invokeZS?this._invokeZS.onInvoke(this._invokeDlgt,this._invokeCurrZone,v,y,g,j,vn):y.apply(g,j)}handleError(v,y){return this._handleErrorZS?this._handleErrorZS.onHandleError(this._handleErrorDlgt,this._handleErrorCurrZone,v,y):!0}scheduleTask(v,y){let g=y;if(this._scheduleTaskZS)this._hasTaskZS&&g._zoneDelegates.push(this._hasTaskDlgtOwner),g=this._scheduleTaskZS.onScheduleTask(this._scheduleTaskDlgt,this._scheduleTaskCurrZone,v,y),g||(g=y);else if(y.scheduleFn)y.scheduleFn(y);else if(y.type==M)Rn(y);else throw new Error("Task is missing scheduleFn.");return g}invokeTask(v,y,g,j){return this._invokeTaskZS?this._invokeTaskZS.onInvokeTask(this._invokeTaskDlgt,this._invokeTaskCurrZone,v,y,g,j):y.callback.apply(g,j)}cancelTask(v,y){let g;if(this._cancelTaskZS)g=this._cancelTaskZS.onCancelTask(this._cancelTaskDlgt,this._cancelTaskCurrZone,v,y);else{if(!y.cancelFn)throw Error("Task is not cancelable");g=y.cancelFn(y)}return g}hasTask(v,y){try{this._hasTaskZS&&this._hasTaskZS.onHasTask(this._hasTaskDlgt,this._hasTaskCurrZone,v,y)}catch(g){this.handleError(v,g)}}_updateTaskCount(v,y){const g=this._taskCounts,j=g[v],vn=g[v]=j+y;if(vn<0)throw new Error("More tasks executed then were scheduled.");if(j==0||vn==0){const en={microTask:g.microTask>0,macroTask:g.macroTask>0,eventTask:g.eventTask>0,change:v};this.hasTask(this._zone,en)}}}class R{constructor(v,y,g,j,vn,en){if(u(this,"type"),u(this,"source"),u(this,"invoke"),u(this,"callback"),u(this,"data"),u(this,"scheduleFn"),u(this,"cancelFn"),u(this,"_zone",null),u(this,"runCount",0),u(this,"_zoneDelegates",null),u(this,"_state","notScheduled"),this.type=v,this.source=y,this.data=j,this.scheduleFn=vn,this.cancelFn=en,!g)throw new Error("callback is not defined");this.callback=g;const ht=this;v===Q&&j&&j.useG?this.invoke=R.invokeTask:this.invoke=function(){return R.invokeTask.call(c,ht,this,arguments)}}static invokeTask(v,y,g){v||(v=this),et++;try{return v.runCount++,v.zone.runTask(v,y,g)}finally{et==1&&Qn(),et--}}get zone(){return this._zone}get state(){return this._state}cancelScheduleRequest(){this._transitionTo(N,I)}_transitionTo(v,y,g){if(this._state===y||this._state===g)this._state=v,v==N&&(this._zoneDelegates=null);else throw new Error(`${this.type} '${this.source}': can not transition to '${v}', expecting state '${y}'${g?" or '"+g+"'":""}, was '${this._state}'.`)}toString(){return this.data&&typeof this.data.handleId<"u"?this.data.handleId.toString():Object.prototype.toString.call(this)}toJSON(){return{type:this.type,state:this.state,source:this.source,zone:this.zone.name,runCount:this.runCount}}}const B=f("setTimeout"),$=f("Promise"),P=f("then");let G=[],rn=!1,sn;function Wn(yn){if(sn||c[$]&&(sn=c[$].resolve(0)),sn){let v=sn[P];v||(v=sn.then),v.call(sn,yn)}else c[B](yn,0)}function Rn(yn){et===0&&G.length===0&&Wn(Qn),yn&&G.push(yn)}function Qn(){if(!rn){for(rn=!0;G.length;){const yn=G;G=[];for(let v=0;v<yn.length;v++){const y=yn[v];try{y.zone.runTask(y,null,null)}catch(g){tn.onUnhandledError(g)}}}tn.microtaskDrainDone(),rn=!1}}const Un={name:"NO ZONE"},N="notScheduled",I="scheduling",wn="scheduled",pn="running",Sn="canceling",bn="unknown",M="microTask",On="macroTask",Q="eventTask",W={},tn={symbol:f,currentZoneFrame:()=>U,onUnhandledError:_n,microtaskDrainDone:_n,scheduleMicroTask:Rn,showUncaughtError:()=>!_[f("ignoreConsoleErrorUncaughtError")],patchEventTarget:()=>[],patchOnProperties:_n,patchMethod:()=>_n,bindArguments:()=>[],patchThen:()=>_n,patchMacroTask:()=>_n,patchEventPrototype:()=>_n,getGlobalObjects:()=>{},ObjectDefineProperty:()=>_n,ObjectGetOwnPropertyDescriptor:()=>{},ObjectCreate:()=>{},ArraySlice:()=>[],patchClass:()=>_n,wrapWithCurrentZone:()=>_n,filterProperties:()=>[],attachOriginToPatched:()=>_n,_redefineProperty:()=>_n,patchCallbacks:()=>_n,nativeScheduleMicroTask:Wn};let U={parent:null,zone:new _(null,null)},Yn=null,et=0;function _n(){}return D("Zone","Zone"),_}function p(){var h;const d=globalThis,D=d[f("forceDuplicateZoneCheck")]===!0;if(d.Zone&&(D||typeof d.Zone.__symbol__!="function"))throw new Error("Zone already loaded.");return(h=d.Zone)!=null||(d.Zone=b()),d.Zone}var E=Object.getOwnPropertyDescriptor,A=Object.defineProperty,F=Object.getPrototypeOf,S=Object.create,gn=Array.prototype.slice,tt="addEventListener",kn="removeEventListener",ot=f(tt),Rt=f(kn),Vn="true",Ot="false",Ke=f("");function Bi(h,d){return Zone.current.wrap(h,d)}function ji(h,d,D,m,_){return Zone.current.scheduleMacroTask(h,d,D,m,_)}var hn=f,Ar=typeof window<"u",Sr=Ar?window:void 0,Hn=Ar&&Sr||globalThis,nh="removeAttribute";function Hi(h,d){for(let D=h.length-1;D>=0;D--)typeof h[D]=="function"&&(h[D]=Bi(h[D],d+"_"+D));return h}function th(h,d){const D=h.constructor.name;for(let m=0;m<d.length;m++){const _=d[m],k=h[_];if(k){const H=E(h,_);if(!Aa(H))continue;h[_]=(R=>{const B=function(){return R.apply(this,Hi(arguments,D+"."+_))};return Mt(B,R),B})(k)}}}function Aa(h){return h?h.writable===!1?!1:!(typeof h.get=="function"&&typeof h.set>"u"):!0}var Sa=typeof WorkerGlobalScope<"u"&&self instanceof WorkerGlobalScope,Fr=!("nw"in Hn)&&typeof Hn.process<"u"&&Hn.process.toString()==="[object process]",Ui=!Fr&&!Sa&&!!(Ar&&Sr.HTMLElement),Fa=typeof Hn.process<"u"&&Hn.process.toString()==="[object process]"&&!Sa&&!!(Ar&&Sr.HTMLElement),Rr={},eh=hn("enable_beforeunload"),Ra=function(h){if(h=h||Hn.event,!h)return;let d=Rr[h.type];d||(d=Rr[h.type]=hn("ON_PROPERTY"+h.type));const D=this||h.target||Hn,m=D[d];let _;if(Ui&&D===Sr&&h.type==="error"){const k=h;_=m&&m.call(this,k.message,k.filename,k.lineno,k.colno,k.error),_===!0&&h.preventDefault()}else _=m&&m.apply(this,arguments),h.type==="beforeunload"&&Hn[eh]&&typeof _=="string"?h.returnValue=_:_!=null&&!_&&h.preventDefault();return _};function Oa(h,d,D){let m=E(h,d);if(!m&&D&&E(D,d)&&(m={enumerable:!0,configurable:!0}),!m||!m.configurable)return;const _=hn("on"+d+"patched");if(h.hasOwnProperty(_)&&h[_])return;delete m.writable,delete m.value;const k=m.get,H=m.set,R=d.slice(2);let B=Rr[R];B||(B=Rr[R]=hn("ON_PROPERTY"+R)),m.set=function($){let P=this;if(!P&&h===Hn&&(P=Hn),!P)return;typeof P[B]=="function"&&P.removeEventListener(R,Ra),H?.call(P,null),P[B]=$,typeof $=="function"&&P.addEventListener(R,Ra,!1)},m.get=function(){let $=this;if(!$&&h===Hn&&($=Hn),!$)return null;const P=$[B];if(P)return P;if(k){let G=k.call(this);if(G)return m.set.call(this,G),typeof $[nh]=="function"&&$.removeAttribute(d),G}return null},A(h,d,m),h[_]=!0}function Pa(h,d,D){if(d)for(let m=0;m<d.length;m++)Oa(h,"on"+d[m],D);else{const m=[];for(const _ in h)_.slice(0,2)=="on"&&m.push(_);for(let _=0;_<m.length;_++)Oa(h,m[_],D)}}var dt=hn("originalInstance");function nr(h){const d=Hn[h];if(!d)return;Hn[hn(h)]=d,Hn[h]=function(){const _=Hi(arguments,h);switch(_.length){case 0:this[dt]=new d;break;case 1:this[dt]=new d(_[0]);break;case 2:this[dt]=new d(_[0],_[1]);break;case 3:this[dt]=new d(_[0],_[1],_[2]);break;case 4:this[dt]=new d(_[0],_[1],_[2],_[3]);break;default:throw new Error("Arg list too long.")}},Mt(Hn[h],d);const D=new d(function(){});let m;for(m in D)h==="XMLHttpRequest"&&m==="responseBlob"||(function(_){typeof D[_]=="function"?Hn[h].prototype[_]=function(){return this[dt][_].apply(this[dt],arguments)}:A(Hn[h].prototype,_,{set:function(k){typeof k=="function"?(this[dt][_]=Bi(k,h+"."+_),Mt(this[dt][_],k)):this[dt][_]=k},get:function(){return this[dt][_]}})})(m);for(m in d)m!=="prototype"&&d.hasOwnProperty(m)&&(Hn[h][m]=d[m])}function Pt(h,d,D){let m=h;for(;m&&!m.hasOwnProperty(d);)m=F(m);!m&&h[d]&&(m=h);const _=hn(d);let k=null;if(m&&(!(k=m[_])||!m.hasOwnProperty(_))){k=m[_]=m[d];const H=m&&E(m,d);if(Aa(H)){const R=D(k,_,d);m[d]=function(){return R(this,arguments)},Mt(m[d],k)}}return k}function rh(h,d,D){let m=null;function _(k){const H=k.data;return H.args[H.cbIdx]=function(){k.invoke.apply(this,arguments)},m.apply(H.target,H.args),k}m=Pt(h,d,k=>function(H,R){const B=D(H,R);return B.cbIdx>=0&&typeof R[B.cbIdx]=="function"?ji(B.name,R[B.cbIdx],B,_):k.apply(H,R)})}function Mt(h,d){h[hn("OriginalDelegate")]=d}function Ma(h){return typeof h=="function"}function La(h){return typeof h=="number"}var ih={useG:!0},st={},Na={},Ba=new RegExp("^"+Ke+"(\\w+)(true|false)$"),ja=hn("propagationStopped");function Ha(h,d){const D=(d?d(h):h)+Ot,m=(d?d(h):h)+Vn,_=Ke+D,k=Ke+m;st[h]={},st[h][Ot]=_,st[h][Vn]=k}function oh(h,d,D,m){const _=m&&m.add||tt,k=m&&m.rm||kn,H=m&&m.listeners||"eventListeners",R=m&&m.rmAll||"removeAllListeners",B=hn(_),$="."+_+":",P="prependListener",G="."+P+":",rn=function(N,I,wn){if(N.isRemoved)return;const pn=N.callback;typeof pn=="object"&&pn.handleEvent&&(N.callback=M=>pn.handleEvent(M),N.originalDelegate=pn);let Sn;try{N.invoke(N,I,[wn])}catch(M){Sn=M}const bn=N.options;if(bn&&typeof bn=="object"&&bn.once){const M=N.originalDelegate?N.originalDelegate:N.callback;I[k].call(I,wn.type,M,bn)}return Sn};function sn(N,I,wn){if(I=I||h.event,!I)return;const pn=N||I.target||h,Sn=pn[st[I.type][wn?Vn:Ot]];if(Sn){const bn=[];if(Sn.length===1){const M=rn(Sn[0],pn,I);M&&bn.push(M)}else{const M=Sn.slice();for(let On=0;On<M.length&&!(I&&I[ja]===!0);On++){const Q=rn(M[On],pn,I);Q&&bn.push(Q)}}if(bn.length===1)throw bn[0];for(let M=0;M<bn.length;M++){const On=bn[M];d.nativeScheduleMicroTask(()=>{throw On})}}}const Wn=function(N){return sn(this,N,!1)},Rn=function(N){return sn(this,N,!0)};function Qn(N,I){if(!N)return!1;let wn=!0;I&&I.useG!==void 0&&(wn=I.useG);const pn=I&&I.vh;let Sn=!0;I&&I.chkDup!==void 0&&(Sn=I.chkDup);let bn=!1;I&&I.rt!==void 0&&(bn=I.rt);let M=N;for(;M&&!M.hasOwnProperty(_);)M=F(M);if(!M&&N[_]&&(M=N),!M||M[B])return!1;const On=I&&I.eventNameToString,Q={},W=M[B]=M[_],tn=M[hn(k)]=M[k],U=M[hn(H)]=M[H],Yn=M[hn(R)]=M[R];let et;I&&I.prepend&&(et=M[hn(I.prepend)]=M[I.prepend]);function _n(w,T){return T?typeof w=="boolean"?{capture:w,passive:!0}:w?typeof w=="object"&&w.passive!==!1?l(a({},w),{passive:!0}):w:{passive:!0}:w}const yn=function(w){if(!Q.isExisting)return W.call(Q.target,Q.eventName,Q.capture?Rn:Wn,Q.options)},v=function(w){if(!w.isRemoved){const T=st[w.eventName];let L;T&&(L=T[w.capture?Vn:Ot]);const Z=L&&w.target[L];if(Z){for(let O=0;O<Z.length;O++)if(Z[O]===w){Z.splice(O,1),w.isRemoved=!0,w.removeAbortListener&&(w.removeAbortListener(),w.removeAbortListener=null),Z.length===0&&(w.allRemoved=!0,w.target[L]=null);break}}}if(w.allRemoved)return tn.call(w.target,w.eventName,w.capture?Rn:Wn,w.options)},y=function(w){return W.call(Q.target,Q.eventName,w.invoke,Q.options)},g=function(w){return et.call(Q.target,Q.eventName,w.invoke,Q.options)},j=function(w){return tn.call(w.target,w.eventName,w.invoke,w.options)},vn=wn?yn:y,en=wn?v:j,ht=function(w,T){const L=typeof T;return L==="function"&&w.callback===T||L==="object"&&w.originalDelegate===T},Lt=I?.diff||ht,Nt=Zone[hn("UNPATCHED_EVENTS")],pt=h[hn("PASSIVE_EVENTS")];function x(w){if(typeof w=="object"&&w!==null){const T=a({},w);return w.signal&&(T.signal=w.signal),T}return w}const C=function(w,T,L,Z,O=!1,J=!1){return function(){const K=this||h;let nn=arguments[0];I&&I.transferEventName&&(nn=I.transferEventName(nn));let xn=arguments[1];if(!xn)return w.apply(this,arguments);if(Fr&&nn==="uncaughtException")return w.apply(this,arguments);let Tn=!1;if(typeof xn!="function"){if(!xn.handleEvent)return w.apply(this,arguments);Tn=!0}if(pn&&!pn(w,xn,K,arguments))return;const qt=!!pt&&pt.indexOf(nn)!==-1,mt=x(_n(arguments[2],qt)),ce=mt?.signal;if(ce?.aborted)return;if(Nt){for(let xt=0;xt<Nt.length;xt++)if(nn===Nt[xt])return qt?w.call(K,nn,xn,mt):w.apply(this,arguments)}const Vi=mt?typeof mt=="boolean"?!0:mt.capture:!1,Ga=mt&&typeof mt=="object"?mt.once:!1,vh=Zone.current;let Zi=st[nn];Zi||(Ha(nn,On),Zi=st[nn]);const $a=Zi[Vi?Vn:Ot];let Ce=K[$a],Wa=!1;if(Ce){if(Wa=!0,Sn){for(let xt=0;xt<Ce.length;xt++)if(Lt(Ce[xt],xn))return}}else Ce=K[$a]=[];let Pr;const Ya=K.constructor.name,Xa=Na[Ya];Xa&&(Pr=Xa[nn]),Pr||(Pr=Ya+T+(On?On(nn):nn)),Q.options=mt,Ga&&(Q.options.once=!1),Q.target=K,Q.capture=Vi,Q.eventName=nn,Q.isExisting=Wa;const tr=wn?ih:void 0;tr&&(tr.taskData=Q),ce&&(Q.options.signal=void 0);const gt=vh.scheduleEventTask(Pr,xn,tr,L,Z);if(ce){Q.options.signal=ce;const xt=()=>gt.zone.cancelTask(gt);w.call(ce,"abort",xt,{once:!0}),gt.removeAbortListener=()=>ce.removeEventListener("abort",xt)}if(Q.target=null,tr&&(tr.taskData=null),Ga&&(Q.options.once=!0),typeof gt.options!="boolean"&&(gt.options=mt),gt.target=K,gt.capture=Vi,gt.eventName=nn,Tn&&(gt.originalDelegate=xn),J?Ce.unshift(gt):Ce.push(gt),O)return K}};return M[_]=C(W,$,vn,en,bn),et&&(M[P]=C(et,G,g,en,bn,!0)),M[k]=function(){const w=this||h;let T=arguments[0];I&&I.transferEventName&&(T=I.transferEventName(T));const L=arguments[2],Z=L?typeof L=="boolean"?!0:L.capture:!1,O=arguments[1];if(!O)return tn.apply(this,arguments);if(pn&&!pn(tn,O,w,arguments))return;const J=st[T];let K;J&&(K=J[Z?Vn:Ot]);const nn=K&&w[K];if(nn)for(let xn=0;xn<nn.length;xn++){const Tn=nn[xn];if(Lt(Tn,O)){if(nn.splice(xn,1),Tn.isRemoved=!0,nn.length===0&&(Tn.allRemoved=!0,w[K]=null,!Z&&typeof T=="string")){const qt=Ke+"ON_PROPERTY"+T;w[qt]=null}return Tn.zone.cancelTask(Tn),bn?w:void 0}}return tn.apply(this,arguments)},M[H]=function(){const w=this||h;let T=arguments[0];I&&I.transferEventName&&(T=I.transferEventName(T));const L=[],Z=Ua(w,On?On(T):T);for(let O=0;O<Z.length;O++){const J=Z[O];let K=J.originalDelegate?J.originalDelegate:J.callback;L.push(K)}return L},M[R]=function(){const w=this||h;let T=arguments[0];if(T){I&&I.transferEventName&&(T=I.transferEventName(T));const L=st[T];if(L){const Z=L[Ot],O=L[Vn],J=w[Z],K=w[O];if(J){const nn=J.slice();for(let xn=0;xn<nn.length;xn++){const Tn=nn[xn];let qt=Tn.originalDelegate?Tn.originalDelegate:Tn.callback;this[k].call(this,T,qt,Tn.options)}}if(K){const nn=K.slice();for(let xn=0;xn<nn.length;xn++){const Tn=nn[xn];let qt=Tn.originalDelegate?Tn.originalDelegate:Tn.callback;this[k].call(this,T,qt,Tn.options)}}}}else{const L=Object.keys(w);for(let Z=0;Z<L.length;Z++){const O=L[Z],J=Ba.exec(O);let K=J&&J[1];K&&K!=="removeListener"&&this[R].call(this,K)}this[R].call(this,"removeListener")}if(bn)return this},Mt(M[_],W),Mt(M[k],tn),Yn&&Mt(M[R],Yn),U&&Mt(M[H],U),!0}let Un=[];for(let N=0;N<D.length;N++)Un[N]=Qn(D[N],m);return Un}function Ua(h,d){if(!d){const k=[];for(let H in h){const R=Ba.exec(H);let B=R&&R[1];if(B&&(!d||B===d)){const $=h[H];if($)for(let P=0;P<$.length;P++)k.push($[P])}}return k}let D=st[d];D||(Ha(d),D=st[d]);const m=h[D[Ot]],_=h[D[Vn]];return m?_?m.concat(_):m.slice():_?_.slice():[]}function sh(h,d){const D=h.Event;D&&D.prototype&&d.patchMethod(D.prototype,"stopImmediatePropagation",m=>function(_,k){_[ja]=!0,m&&m.apply(_,k)})}function ah(h,d){d.patchMethod(h,"queueMicrotask",D=>function(m,_){Zone.current.scheduleMicroTask("queueMicrotask",_[0])})}var Or=hn("zoneTask");function Ee(h,d,D,m){let _=null,k=null;d+=m,D+=m;const H={};function R($){const P=$.data;P.args[0]=function(){return $.invoke.apply(this,arguments)};const G=_.apply(h,P.args);return La(G)?P.handleId=G:(P.handle=G,P.isRefreshable=Ma(G.refresh)),$}function B($){const{handle:P,handleId:G}=$.data;return k.call(h,P??G)}_=Pt(h,d,$=>function(P,G){var rn;if(Ma(G[0])){const sn={isRefreshable:!1,isPeriodic:m==="Interval",delay:m==="Timeout"||m==="Interval"?G[1]||0:void 0,args:G},Wn=G[0];G[0]=function(){try{return Wn.apply(this,arguments)}finally{const{handle:pn,handleId:Sn,isPeriodic:bn,isRefreshable:M}=sn;!bn&&!M&&(Sn?delete H[Sn]:pn&&(pn[Or]=null))}};const Rn=ji(d,G[0],sn,R,B);if(!Rn)return Rn;const{handleId:Qn,handle:Un,isRefreshable:N,isPeriodic:I}=Rn.data;if(Qn)H[Qn]=Rn;else if(Un&&(Un[Or]=Rn,N&&!I)){const wn=Un.refresh;Un.refresh=function(){const{zone:pn,state:Sn}=Rn;return Sn==="notScheduled"?(Rn._state="scheduled",pn._updateTaskCount(Rn,1)):Sn==="running"&&(Rn._state="scheduling"),wn.call(this)}}return(rn=Un??Qn)!=null?rn:Rn}else return $.apply(h,G)}),k=Pt(h,D,$=>function(P,G){const rn=G[0];let sn;La(rn)?(sn=H[rn],delete H[rn]):(sn=rn?.[Or],sn?rn[Or]=null:sn=rn),sn?.type?sn.cancelFn&&sn.zone.cancelTask(sn):$.apply(h,G)})}function lh(h,d){const{isBrowser:D,isMix:m}=d.getGlobalObjects();if(!D&&!m||!h.customElements||!("customElements"in h))return;const _=["connectedCallback","disconnectedCallback","adoptedCallback","attributeChangedCallback","formAssociatedCallback","formDisabledCallback","formResetCallback","formStateRestoreCallback"];d.patchCallbacks(d,h.customElements,"customElements","define",_)}function uh(h,d){if(Zone[d.symbol("patchEventTarget")])return;const{eventNames:D,zoneSymbolEventNames:m,TRUE_STR:_,FALSE_STR:k,ZONE_SYMBOL_PREFIX:H}=d.getGlobalObjects();for(let B=0;B<D.length;B++){const $=D[B],P=$+k,G=$+_,rn=H+P,sn=H+G;m[$]={},m[$][k]=rn,m[$][_]=sn}const R=h.EventTarget;if(!(!R||!R.prototype))return d.patchEventTarget(h,d,[R&&R.prototype]),!0}function ch(h,d){d.patchEventPrototype(h,d)}function za(h,d,D){if(!D||D.length===0)return d;const m=D.filter(k=>k.target===h);if(m.length===0)return d;const _=m[0].ignoreProperties;return d.filter(k=>_.indexOf(k)===-1)}function Va(h,d,D,m){if(!h)return;const _=za(h,d,D);Pa(h,_,m)}function zi(h){return Object.getOwnPropertyNames(h).filter(d=>d.startsWith("on")&&d.length>2).map(d=>d.substring(2))}function fh(h,d){if(Fr&&!Fa||Zone[h.symbol("patchEvents")])return;const D=d.__Zone_ignore_on_properties;let m=[];if(Ui){const _=window;m=m.concat(["Document","SVGElement","Element","HTMLElement","HTMLBodyElement","HTMLMediaElement","HTMLFrameSetElement","HTMLFrameElement","HTMLIFrameElement","HTMLMarqueeElement","Worker"]),Va(_,zi(_),D,F(_))}m=m.concat(["XMLHttpRequest","XMLHttpRequestEventTarget","IDBIndex","IDBRequest","IDBOpenDBRequest","IDBDatabase","IDBTransaction","IDBCursor","WebSocket"]);for(let _=0;_<m.length;_++){const k=d[m[_]];k?.prototype&&Va(k.prototype,zi(k.prototype),D)}}function dh(h){h.__load_patch("timers",d=>{const m="clear";Ee(d,"set",m,"Timeout"),Ee(d,"set",m,"Interval"),Ee(d,"set",m,"Immediate")}),h.__load_patch("requestAnimationFrame",d=>{Ee(d,"request","cancel","AnimationFrame"),Ee(d,"mozRequest","mozCancel","AnimationFrame"),Ee(d,"webkitRequest","webkitCancel","AnimationFrame")}),h.__load_patch("blocking",(d,D)=>{const m=["alert","prompt","confirm"];for(let _=0;_<m.length;_++){const k=m[_];Pt(d,k,(H,R,B)=>function($,P){return D.current.run(H,d,P,B)})}}),h.__load_patch("EventTarget",(d,D,m)=>{ch(d,m),uh(d,m);const _=d.XMLHttpRequestEventTarget;_&&_.prototype&&m.patchEventTarget(d,m,[_.prototype])}),h.__load_patch("MutationObserver",(d,D,m)=>{nr("MutationObserver"),nr("WebKitMutationObserver")}),h.__load_patch("IntersectionObserver",(d,D,m)=>{nr("IntersectionObserver")}),h.__load_patch("FileReader",(d,D,m)=>{nr("FileReader")}),h.__load_patch("on_property",(d,D,m)=>{fh(m,d)}),h.__load_patch("customElements",(d,D,m)=>{lh(d,m)}),h.__load_patch("XHR",(d,D)=>{$(d);const m=hn("xhrTask"),_=hn("xhrSync"),k=hn("xhrListener"),H=hn("xhrScheduled"),R=hn("xhrURL"),B=hn("xhrErrorBeforeScheduled");function $(P){const G=P.XMLHttpRequest;if(!G)return;const rn=G.prototype;function sn(W){return W[m]}let Wn=rn[ot],Rn=rn[Rt];if(!Wn){const W=P.XMLHttpRequestEventTarget;if(W){const tn=W.prototype;Wn=tn[ot],Rn=tn[Rt]}}const Qn="readystatechange",Un="scheduled";function N(W){const tn=W.data,U=tn.target;U[H]=!1,U[B]=!1;const Yn=U[k];Wn||(Wn=U[ot],Rn=U[Rt]),Yn&&Rn.call(U,Qn,Yn);const et=U[k]=()=>{if(U.readyState===U.DONE)if(!tn.aborted&&U[H]&&W.state===Un){const yn=U[D.__symbol__("loadfalse")];if(U.status!==0&&yn&&yn.length>0){const v=W.invoke;W.invoke=function(){const y=U[D.__symbol__("loadfalse")];for(let g=0;g<y.length;g++)y[g]===W&&y.splice(g,1);!tn.aborted&&W.state===Un&&v.call(W)},yn.push(W)}else W.invoke()}else!tn.aborted&&U[H]===!1&&(U[B]=!0)};return Wn.call(U,Qn,et),U[m]||(U[m]=W),On.apply(U,tn.args),U[H]=!0,W}function I(){}function wn(W){const tn=W.data;return tn.aborted=!0,Q.apply(tn.target,tn.args)}const pn=Pt(rn,"open",()=>function(W,tn){return W[_]=tn[2]==!1,W[R]=tn[1],pn.apply(W,tn)}),Sn="XMLHttpRequest.send",bn=hn("fetchTaskAborting"),M=hn("fetchTaskScheduling"),On=Pt(rn,"send",()=>function(W,tn){if(D.current[M]===!0||W[_])return On.apply(W,tn);{const U={target:W,url:W[R],isPeriodic:!1,args:tn,aborted:!1},Yn=ji(Sn,I,U,N,wn);W&&W[B]===!0&&!U.aborted&&Yn.state===Un&&Yn.invoke()}}),Q=Pt(rn,"abort",()=>function(W,tn){const U=sn(W);if(U&&typeof U.type=="string"){if(U.cancelFn==null||U.data&&U.data.aborted)return;U.zone.cancelTask(U)}else if(D.current[bn]===!0)return Q.apply(W,tn)})}}),h.__load_patch("geolocation",d=>{d.navigator&&d.navigator.geolocation&&th(d.navigator.geolocation,["getCurrentPosition","watchPosition"])}),h.__load_patch("PromiseRejectionEvent",(d,D)=>{function m(_){return function(k){Ua(d,_).forEach(R=>{const B=d.PromiseRejectionEvent;if(B){const $=new B(_,{promise:k.promise,reason:k.rejection});R.invoke($)}})}}d.PromiseRejectionEvent&&(D[hn("unhandledPromiseRejectionHandler")]=m("unhandledrejection"),D[hn("rejectionHandledHandler")]=m("rejectionhandled"))}),h.__load_patch("queueMicrotask",(d,D,m)=>{ah(d,m)})}function hh(h){h.__load_patch("ZoneAwarePromise",(d,D,m)=>{const _=Object.getOwnPropertyDescriptor,k=Object.defineProperty;function H(x){if(x&&x.toString===Object.prototype.toString){const C=x.constructor&&x.constructor.name;return(C||"")+": "+JSON.stringify(x)}return x?x.toString():Object.prototype.toString.call(x)}const R=m.symbol,B=[],$=d[R("DISABLE_WRAPPING_UNCAUGHT_PROMISE_REJECTION")]!==!1,P=R("Promise"),G=R("then"),rn="__creationTrace__";m.onUnhandledError=x=>{if(m.showUncaughtError()){const C=x&&x.rejection;C?console.error("Unhandled Promise rejection:",C instanceof Error?C.message:C,"; Zone:",x.zone.name,"; Task:",x.task&&x.task.source,"; Value:",C,C instanceof Error?C.stack:void 0):console.error(x)}},m.microtaskDrainDone=()=>{for(;B.length;){const x=B.shift();try{x.zone.runGuarded(()=>{throw x.throwOriginal?x.rejection:x})}catch(C){Wn(C)}}};const sn=R("unhandledPromiseRejectionHandler");function Wn(x){m.onUnhandledError(x);try{const C=D[sn];typeof C=="function"&&C.call(this,x)}catch{}}function Rn(x){return x&&typeof x.then=="function"}function Qn(x){return x}function Un(x){return en.reject(x)}const N=R("state"),I=R("value"),wn=R("finally"),pn=R("parentPromiseValue"),Sn=R("parentPromiseState"),bn="Promise.then",M=null,On=!0,Q=!1,W=0;function tn(x,C){return w=>{try{_n(x,C,w)}catch(T){_n(x,!1,T)}}}const U=function(){let x=!1;return function(w){return function(){x||(x=!0,w.apply(null,arguments))}}},Yn="Promise resolved with itself",et=R("currentTaskTrace");function _n(x,C,w){const T=U();if(x===w)throw new TypeError(Yn);if(x[N]===M){let L=null;try{(typeof w=="object"||typeof w=="function")&&(L=w&&w.then)}catch(Z){return T(()=>{_n(x,!1,Z)})(),x}if(C!==Q&&w instanceof en&&w.hasOwnProperty(N)&&w.hasOwnProperty(I)&&w[N]!==M)v(w),_n(x,w[N],w[I]);else if(C!==Q&&typeof L=="function")try{L.call(w,T(tn(x,C)),T(tn(x,!1)))}catch(Z){T(()=>{_n(x,!1,Z)})()}else{x[N]=C;const Z=x[I];if(x[I]=w,x[wn]===wn&&C===On&&(x[N]=x[Sn],x[I]=x[pn]),C===Q&&w instanceof Error){const O=D.currentTask&&D.currentTask.data&&D.currentTask.data[rn];O&&k(w,et,{configurable:!0,enumerable:!1,writable:!0,value:O})}for(let O=0;O<Z.length;)y(x,Z[O++],Z[O++],Z[O++],Z[O++]);if(Z.length==0&&C==Q){x[N]=W;let O=w;try{throw new Error("Uncaught (in promise): "+H(w)+(w&&w.stack?`
`+w.stack:""))}catch(J){O=J}$&&(O.throwOriginal=!0),O.rejection=w,O.promise=x,O.zone=D.current,O.task=D.currentTask,B.push(O),m.scheduleMicroTask()}}}return x}const yn=R("rejectionHandledHandler");function v(x){if(x[N]===W){try{const C=D[yn];C&&typeof C=="function"&&C.call(this,{rejection:x[I],promise:x})}catch{}x[N]=Q;for(let C=0;C<B.length;C++)x===B[C].promise&&B.splice(C,1)}}function y(x,C,w,T,L){v(x);const Z=x[N],O=Z?typeof T=="function"?T:Qn:typeof L=="function"?L:Un;C.scheduleMicroTask(bn,()=>{try{const J=x[I],K=!!w&&wn===w[wn];K&&(w[pn]=J,w[Sn]=Z);const nn=C.run(O,void 0,K&&O!==Un&&O!==Qn?[]:[J]);_n(w,!0,nn)}catch(J){_n(w,!1,J)}},w)}const g="function ZoneAwarePromise() { [native code] }",j=function(){},vn=d.AggregateError;class en{static toString(){return g}static resolve(C){return C instanceof en?C:_n(new this(null),On,C)}static reject(C){return _n(new this(null),Q,C)}static withResolvers(){const C={};return C.promise=new en((w,T)=>{C.resolve=w,C.reject=T}),C}static any(C){if(!C||typeof C[Symbol.iterator]!="function")return Promise.reject(new vn([],"All promises were rejected"));const w=[];let T=0;try{for(let O of C)T++,w.push(en.resolve(O))}catch{return Promise.reject(new vn([],"All promises were rejected"))}if(T===0)return Promise.reject(new vn([],"All promises were rejected"));let L=!1;const Z=[];return new en((O,J)=>{for(let K=0;K<w.length;K++)w[K].then(nn=>{L||(L=!0,O(nn))},nn=>{Z.push(nn),T--,T===0&&(L=!0,J(new vn(Z,"All promises were rejected")))})})}static race(C){let w,T,L=new this((J,K)=>{w=J,T=K});function Z(J){w(J)}function O(J){T(J)}for(let J of C)Rn(J)||(J=this.resolve(J)),J.then(Z,O);return L}static all(C){return en.allWithCallback(C)}static allSettled(C){return(this&&this.prototype instanceof en?this:en).allWithCallback(C,{thenCallback:T=>({status:"fulfilled",value:T}),errorCallback:T=>({status:"rejected",reason:T})})}static allWithCallback(C,w){let T,L,Z=new this((nn,xn)=>{T=nn,L=xn}),O=2,J=0;const K=[];for(let nn of C){Rn(nn)||(nn=this.resolve(nn));const xn=J;try{nn.then(Tn=>{K[xn]=w?w.thenCallback(Tn):Tn,O--,O===0&&T(K)},Tn=>{w?(K[xn]=w.errorCallback(Tn),O--,O===0&&T(K)):L(Tn)})}catch(Tn){L(Tn)}O++,J++}return O-=2,O===0&&T(K),Z}constructor(C){const w=this;if(!(w instanceof en))throw new Error("Must be an instanceof Promise.");w[N]=M,w[I]=[];try{const T=U();C&&C(T(tn(w,On)),T(tn(w,Q)))}catch(T){_n(w,!1,T)}}get[Symbol.toStringTag](){return"Promise"}get[Symbol.species](){return en}then(C,w){var T;let L=(T=this.constructor)==null?void 0:T[Symbol.species];(!L||typeof L!="function")&&(L=this.constructor||en);const Z=new L(j),O=D.current;return this[N]==M?this[I].push(O,Z,C,w):y(this,O,Z,C,w),Z}catch(C){return this.then(null,C)}finally(C){var w;let T=(w=this.constructor)==null?void 0:w[Symbol.species];(!T||typeof T!="function")&&(T=en);const L=new T(j);L[wn]=wn;const Z=D.current;return this[N]==M?this[I].push(Z,L,C,C):y(this,Z,L,C,C),L}}en.resolve=en.resolve,en.reject=en.reject,en.race=en.race,en.all=en.all;const ht=d[P]=d.Promise;d.Promise=en;const Lt=R("thenPatched");function Nt(x){const C=x.prototype,w=_(C,"then");if(w&&(w.writable===!1||!w.configurable))return;const T=C.then;C[G]=T,x.prototype.then=function(L,Z){return new en((J,K)=>{T.call(this,J,K)}).then(L,Z)},x[Lt]=!0}m.patchThen=Nt;function pt(x){return function(C,w){let T=x.apply(C,w);if(T instanceof en)return T;let L=T.constructor;return L[Lt]||Nt(L),T}}if(ht){Nt(ht);const x=ht.try;x&&typeof x=="function"&&(en.try=x),Pt(d,"fetch",C=>pt(C))}return Promise[D.__symbol__("uncaughtPromiseErrors")]=B,en})}function ph(h){h.__load_patch("toString",d=>{const D=Function.prototype.toString,m=hn("OriginalDelegate"),_=hn("Promise"),k=hn("Error"),H=function(){if(typeof this=="function"){const P=this[m];if(P)return typeof P=="function"?D.call(P):Object.prototype.toString.call(P);if(this===Promise){const G=d[_];if(G)return D.call(G)}if(this===Error){const G=d[k];if(G)return D.call(G)}}return D.call(this)};H[m]=D,Function.prototype.toString=H;const R=Object.prototype.toString,B="[object Promise]";Object.prototype.toString=function(){return typeof Promise=="function"&&this instanceof Promise?B:R.call(this)}})}function mh(h,d,D,m,_){const k=Zone.__symbol__(m);if(d[k])return;const H=d[k]=d[m];d[m]=function(R,B,$){return B&&B.prototype&&_.forEach(function(P){const G=`${D}.${m}::`+P,rn=B.prototype;try{if(rn.hasOwnProperty(P)){const sn=h.ObjectGetOwnPropertyDescriptor(rn,P);sn&&sn.value?(sn.value=h.wrapWithCurrentZone(sn.value,G),h._redefineProperty(B.prototype,P,sn)):rn[P]&&(rn[P]=h.wrapWithCurrentZone(rn[P],G))}else rn[P]&&(rn[P]=h.wrapWithCurrentZone(rn[P],G))}catch{}}),H.call(d,R,B,$)},h.attachOriginToPatched(d[m],H)}function gh(h){h.__load_patch("util",(d,D,m)=>{const _=zi(d);m.patchOnProperties=Pa,m.patchMethod=Pt,m.bindArguments=Hi,m.patchMacroTask=rh;const k=D.__symbol__("BLACK_LISTED_EVENTS"),H=D.__symbol__("UNPATCHED_EVENTS");d[H]&&(d[k]=d[H]),d[k]&&(D[k]=D[H]=d[k]),m.patchEventPrototype=sh,m.patchEventTarget=oh,m.ObjectDefineProperty=A,m.ObjectGetOwnPropertyDescriptor=E,m.ObjectCreate=S,m.ArraySlice=gn,m.patchClass=nr,m.wrapWithCurrentZone=Bi,m.filterProperties=za,m.attachOriginToPatched=Mt,m._redefineProperty=Object.defineProperty,m.patchCallbacks=mh,m.getGlobalObjects=()=>({globalSources:Na,zoneSymbolEventNames:st,eventNames:_,isBrowser:Ui,isMix:Fa,isNode:Fr,TRUE_STR:Vn,FALSE_STR:Ot,ZONE_SYMBOL_PREFIX:Ke,ADD_EVENT_LISTENER_STR:tt,REMOVE_EVENT_LISTENER_STR:kn})})}function yh(h){hh(h),ph(h),gh(h)}var Za=p();return yh(Za),dh(Za),qa}wh();let Gn=null,$r=!1,ps=1;const dr=Symbol("SIGNAL");function X(n){const t=Gn;return Gn=n,t}function bh(){return Gn}const ms={version:0,lastCleanEpoch:0,dirty:!1,producers:void 0,producersTail:void 0,consumers:void 0,consumersTail:void 0,recomputing:!1,consumerAllowSignalWrites:!1,consumerIsAlwaysLive:!1,kind:"unknown",producerMustRecompute:()=>!1,producerRecomputeValue:()=>{},consumerMarkedDirty:()=>{},consumerOnSignalRead:()=>{}};function _h(n){if($r)throw new Error("");if(Gn===null)return;Gn.consumerOnSignalRead(n);const t=Gn.producersTail;if(t!==void 0&&t.producer===n)return;let e;const r=Gn.recomputing;if(r&&(e=t!==void 0?t.nextProducer:Gn.producers,e!==void 0&&e.producer===n)){Gn.producersTail=e,e.lastReadVersion=n.version;return}const i=n.consumersTail;if(i!==void 0&&i.consumer===Gn&&(!r||Ih(i,Gn)))return;const o=Ge(Gn),s={producer:n,consumer:Gn,nextProducer:e,prevConsumer:i,lastReadVersion:n.version,nextConsumer:void 0};Gn.producersTail=s,t!==void 0?t.nextProducer=s:Gn.producers=s,o&&zu(n,s)}function Dh(){ps++}function Eh(n){if(!(Ge(n)&&!n.dirty)&&!(!n.dirty&&n.lastCleanEpoch===ps)){if(!n.producerMustRecompute(n)&&!Hu(n)){Ja(n);return}n.producerRecomputeValue(n),Ja(n)}}function Bu(n){if(n.consumers===void 0)return;const t=$r;$r=!0;try{for(let e=n.consumers;e!==void 0;e=e.nextConsumer){const r=e.consumer;r.dirty||Ch(r)}}finally{$r=t}}function ju(){return Gn?.consumerAllowSignalWrites!==!1}function Ch(n){n.dirty=!0,Bu(n),n.consumerMarkedDirty?.(n)}function Ja(n){n.dirty=!1,n.lastCleanEpoch=ps}function Ka(n){return n&&kh(n),X(n)}function kh(n){n.producersTail=void 0,n.recomputing=!0}function xh(n,t){X(t),n&&Th(n)}function Th(n){n.recomputing=!1;const t=n.producersTail;let e=t!==void 0?t.nextProducer:n.producers;if(e!==void 0){if(Ge(n))do e=gs(e);while(e!==void 0);t!==void 0?t.nextProducer=void 0:n.producers=void 0}}function Hu(n){for(let t=n.producers;t!==void 0;t=t.nextProducer){const e=t.producer,r=t.lastReadVersion;if(r!==e.version||(Eh(e),r!==e.version))return!0}return!1}function Uu(n){if(Ge(n)){let t=n.producers;for(;t!==void 0;)t=gs(t)}n.producers=void 0,n.producersTail=void 0,n.consumers=void 0,n.consumersTail=void 0}function zu(n,t){const e=n.consumersTail,r=Ge(n);if(e!==void 0?(t.nextConsumer=e.nextConsumer,e.nextConsumer=t):(t.nextConsumer=void 0,n.consumers=t),t.prevConsumer=e,n.consumersTail=t,!r)for(let i=n.producers;i!==void 0;i=i.nextProducer)zu(i.producer,i)}function gs(n){const t=n.producer,e=n.nextProducer,r=n.nextConsumer,i=n.prevConsumer;if(n.nextConsumer=void 0,n.prevConsumer=void 0,r!==void 0?r.prevConsumer=i:t.consumersTail=i,i!==void 0)i.nextConsumer=r;else if(t.consumers=r,!Ge(t)){let o=t.producers;for(;o!==void 0;)o=gs(o)}return e}function Ge(n){return n.consumerIsAlwaysLive||n.consumers!==void 0}function Ih(n,t){const e=t.producersTail;if(e!==void 0){let r=t.producers;do{if(r===n)return!0;if(r===e)break;r=r.nextProducer}while(r!==void 0)}return!1}function Ah(n,t){return Object.is(n,t)}function Sh(){throw new Error}let Vu=Sh;function Zu(n){Vu(n)}function Fh(n){Vu=n}function Rh(n,t){const e=Object.create(Mh);e.value=n,t!==void 0&&(e.equal=t);const r=()=>Oh(e);return r[dr]=e,[r,s=>Gu(e,s),s=>Ph(e,s)]}function Oh(n){return _h(n),n.value}function Gu(n,t){ju()||Zu(n),n.equal(n.value,t)||(n.value=t,Lh(n))}function Ph(n,t){ju()||Zu(n),Gu(n,t(n.value))}const Mh={...ms,equal:Ah,value:void 0,kind:"signal"};function Lh(n){n.version++,Dh(),Bu(n)}var Do=function(n,t){return Do=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,r){e.__proto__=r}||function(e,r){for(var i in r)Object.prototype.hasOwnProperty.call(r,i)&&(e[i]=r[i])},Do(n,t)};function $e(n,t){if(typeof t!="function"&&t!==null)throw new TypeError("Class extends value "+String(t)+" is not a constructor or null");Do(n,t);function e(){this.constructor=n}n.prototype=t===null?Object.create(t):(e.prototype=t.prototype,new e)}function Nh(n,t,e,r){function i(o){return o instanceof e?o:new e(function(s){s(o)})}return new(e||(e=Promise))(function(o,s){function a(c){try{u(r.next(c))}catch(f){s(f)}}function l(c){try{u(r.throw(c))}catch(f){s(f)}}function u(c){c.done?o(c.value):i(c.value).then(a,l)}u((r=r.apply(n,t||[])).next())})}function $u(n,t){var e={label:0,sent:function(){if(o[0]&1)throw o[1];return o[1]},trys:[],ops:[]},r,i,o,s=Object.create((typeof Iterator=="function"?Iterator:Object).prototype);return s.next=a(0),s.throw=a(1),s.return=a(2),typeof Symbol=="function"&&(s[Symbol.iterator]=function(){return this}),s;function a(u){return function(c){return l([u,c])}}function l(u){if(r)throw new TypeError("Generator is already executing.");for(;s&&(s=0,u[0]&&(e=0)),e;)try{if(r=1,i&&(o=u[0]&2?i.return:u[0]?i.throw||((o=i.return)&&o.call(i),0):i.next)&&!(o=o.call(i,u[1])).done)return o;switch(i=0,o&&(u=[u[0]&2,o.value]),u[0]){case 0:case 1:o=u;break;case 4:return e.label++,{value:u[1],done:!1};case 5:e.label++,i=u[1],u=[0];continue;case 7:u=e.ops.pop(),e.trys.pop();continue;default:if(o=e.trys,!(o=o.length>0&&o[o.length-1])&&(u[0]===6||u[0]===2)){e=0;continue}if(u[0]===3&&(!o||u[1]>o[0]&&u[1]<o[3])){e.label=u[1];break}if(u[0]===6&&e.label<o[1]){e.label=o[1],o=u;break}if(o&&e.label<o[2]){e.label=o[2],e.ops.push(u);break}o[2]&&e.ops.pop(),e.trys.pop();continue}u=t.call(n,e)}catch(c){u=[6,c],i=0}finally{r=o=0}if(u[0]&5)throw u[1];return{value:u[0]?u[1]:void 0,done:!0}}}function Le(n){var t=typeof Symbol=="function"&&Symbol.iterator,e=t&&n[t],r=0;if(e)return e.call(n);if(n&&typeof n.length=="number")return{next:function(){return n&&r>=n.length&&(n=void 0),{value:n&&n[r++],done:!n}}};throw new TypeError(t?"Object is not iterable.":"Symbol.iterator is not defined.")}function Eo(n,t){var e=typeof Symbol=="function"&&n[Symbol.iterator];if(!e)return n;var r=e.call(n),i,o=[],s;try{for(;(t===void 0||t-- >0)&&!(i=r.next()).done;)o.push(i.value)}catch(a){s={error:a}}finally{try{i&&!i.done&&(e=r.return)&&e.call(r)}finally{if(s)throw s.error}}return o}function Co(n,t,e){if(e||arguments.length===2)for(var r=0,i=t.length,o;r<i;r++)(o||!(r in t))&&(o||(o=Array.prototype.slice.call(t,0,r)),o[r]=t[r]);return n.concat(o||Array.prototype.slice.call(t))}function Oe(n){return this instanceof Oe?(this.v=n,this):new Oe(n)}function Bh(n,t,e){if(!Symbol.asyncIterator)throw new TypeError("Symbol.asyncIterator is not defined.");var r=e.apply(n,t||[]),i,o=[];return i=Object.create((typeof AsyncIterator=="function"?AsyncIterator:Object).prototype),a("next"),a("throw"),a("return",s),i[Symbol.asyncIterator]=function(){return this},i;function s(p){return function(E){return Promise.resolve(E).then(p,f)}}function a(p,E){r[p]&&(i[p]=function(A){return new Promise(function(F,S){o.push([p,A,F,S])>1||l(p,A)})},E&&(i[p]=E(i[p])))}function l(p,E){try{u(r[p](E))}catch(A){b(o[0][3],A)}}function u(p){p.value instanceof Oe?Promise.resolve(p.value.v).then(c,f):b(o[0][2],p)}function c(p){l("next",p)}function f(p){l("throw",p)}function b(p,E){p(E),o.shift(),o.length&&l(o[0][0],o[0][1])}}function jh(n){if(!Symbol.asyncIterator)throw new TypeError("Symbol.asyncIterator is not defined.");var t=n[Symbol.asyncIterator],e;return t?t.call(n):(n=typeof Le=="function"?Le(n):n[Symbol.iterator](),e={},r("next"),r("throw"),r("return"),e[Symbol.asyncIterator]=function(){return this},e);function r(o){e[o]=n[o]&&function(s){return new Promise(function(a,l){s=n[o](s),i(a,l,s.done,s.value)})}}function i(o,s,a,l){Promise.resolve(l).then(function(u){o({value:u,done:a})},s)}}function Pn(n){return typeof n=="function"}function Wu(n){var t=function(r){Error.call(r),r.stack=new Error().stack},e=n(t);return e.prototype=Object.create(Error.prototype),e.prototype.constructor=e,e}var Gi=Wu(function(n){return function(e){n(this),this.message=e?e.length+` errors occurred during unsubscription:
`+e.map(function(r,i){return i+1+") "+r.toString()}).join(`
  `):"",this.name="UnsubscriptionError",this.errors=e}});function ko(n,t){if(n){var e=n.indexOf(t);0<=e&&n.splice(e,1)}}var We=(function(){function n(t){this.initialTeardown=t,this.closed=!1,this._parentage=null,this._finalizers=null}return n.prototype.unsubscribe=function(){var t,e,r,i,o;if(!this.closed){this.closed=!0;var s=this._parentage;if(s)if(this._parentage=null,Array.isArray(s))try{for(var a=Le(s),l=a.next();!l.done;l=a.next()){var u=l.value;u.remove(this)}}catch(A){t={error:A}}finally{try{l&&!l.done&&(e=a.return)&&e.call(a)}finally{if(t)throw t.error}}else s.remove(this);var c=this.initialTeardown;if(Pn(c))try{c()}catch(A){o=A instanceof Gi?A.errors:[A]}var f=this._finalizers;if(f){this._finalizers=null;try{for(var b=Le(f),p=b.next();!p.done;p=b.next()){var E=p.value;try{nl(E)}catch(A){o=o??[],A instanceof Gi?o=Co(Co([],Eo(o)),Eo(A.errors)):o.push(A)}}}catch(A){r={error:A}}finally{try{p&&!p.done&&(i=b.return)&&i.call(b)}finally{if(r)throw r.error}}}if(o)throw new Gi(o)}},n.prototype.add=function(t){var e;if(t&&t!==this)if(this.closed)nl(t);else{if(t instanceof n){if(t.closed||t._hasParent(this))return;t._addParent(this)}(this._finalizers=(e=this._finalizers)!==null&&e!==void 0?e:[]).push(t)}},n.prototype._hasParent=function(t){var e=this._parentage;return e===t||Array.isArray(e)&&e.includes(t)},n.prototype._addParent=function(t){var e=this._parentage;this._parentage=Array.isArray(e)?(e.push(t),e):e?[e,t]:t},n.prototype._removeParent=function(t){var e=this._parentage;e===t?this._parentage=null:Array.isArray(e)&&ko(e,t)},n.prototype.remove=function(t){var e=this._finalizers;e&&ko(e,t),t instanceof n&&t._removeParent(this)},n.EMPTY=(function(){var t=new n;return t.closed=!0,t})(),n})(),Yu=We.EMPTY;function Xu(n){return n instanceof We||n&&"closed"in n&&Pn(n.remove)&&Pn(n.add)&&Pn(n.unsubscribe)}function nl(n){Pn(n)?n():n.unsubscribe()}var Hh={Promise:void 0},Uh={setTimeout:function(n,t){for(var e=[],r=2;r<arguments.length;r++)e[r-2]=arguments[r];return setTimeout.apply(void 0,Co([n,t],Eo(e)))},clearTimeout:function(n){return clearTimeout(n)},delegate:void 0};function qu(n){Uh.setTimeout(function(){throw n})}function tl(){}function Wr(n){n()}var ys=(function(n){$e(t,n);function t(e){var r=n.call(this)||this;return r.isStopped=!1,e?(r.destination=e,Xu(e)&&e.add(r)):r.destination=Zh,r}return t.create=function(e,r,i){return new xo(e,r,i)},t.prototype.next=function(e){this.isStopped||this._next(e)},t.prototype.error=function(e){this.isStopped||(this.isStopped=!0,this._error(e))},t.prototype.complete=function(){this.isStopped||(this.isStopped=!0,this._complete())},t.prototype.unsubscribe=function(){this.closed||(this.isStopped=!0,n.prototype.unsubscribe.call(this),this.destination=null)},t.prototype._next=function(e){this.destination.next(e)},t.prototype._error=function(e){try{this.destination.error(e)}finally{this.unsubscribe()}},t.prototype._complete=function(){try{this.destination.complete()}finally{this.unsubscribe()}},t})(We),zh=(function(){function n(t){this.partialObserver=t}return n.prototype.next=function(t){var e=this.partialObserver;if(e.next)try{e.next(t)}catch(r){Mr(r)}},n.prototype.error=function(t){var e=this.partialObserver;if(e.error)try{e.error(t)}catch(r){Mr(r)}else Mr(t)},n.prototype.complete=function(){var t=this.partialObserver;if(t.complete)try{t.complete()}catch(e){Mr(e)}},n})(),xo=(function(n){$e(t,n);function t(e,r,i){var o=n.call(this)||this,s;return Pn(e)||!e?s={next:e??void 0,error:r??void 0,complete:i??void 0}:s=e,o.destination=new zh(s),o}return t})(ys);function Mr(n){qu(n)}function Vh(n){throw n}var Zh={closed:!0,next:tl,error:Vh,complete:tl},vs=(function(){return typeof Symbol=="function"&&Symbol.observable||"@@observable"})();function Qu(n){return n}function Gh(n){return n.length===0?Qu:n.length===1?n[0]:function(e){return n.reduce(function(r,i){return i(r)},e)}}var Kn=(function(){function n(t){t&&(this._subscribe=t)}return n.prototype.lift=function(t){var e=new n;return e.source=this,e.operator=t,e},n.prototype.subscribe=function(t,e,r){var i=this,o=Wh(t)?t:new xo(t,e,r);return Wr(function(){var s=i,a=s.operator,l=s.source;o.add(a?a.call(o,l):l?i._subscribe(o):i._trySubscribe(o))}),o},n.prototype._trySubscribe=function(t){try{return this._subscribe(t)}catch(e){t.error(e)}},n.prototype.forEach=function(t,e){var r=this;return e=el(e),new e(function(i,o){var s=new xo({next:function(a){try{t(a)}catch(l){o(l),s.unsubscribe()}},error:o,complete:i});r.subscribe(s)})},n.prototype._subscribe=function(t){var e;return(e=this.source)===null||e===void 0?void 0:e.subscribe(t)},n.prototype[vs]=function(){return this},n.prototype.pipe=function(){for(var t=[],e=0;e<arguments.length;e++)t[e]=arguments[e];return Gh(t)(this)},n.prototype.toPromise=function(t){var e=this;return t=el(t),new t(function(r,i){var o;e.subscribe(function(s){return o=s},function(s){return i(s)},function(){return r(o)})})},n.create=function(t){return new n(t)},n})();function el(n){var t;return(t=n??Hh.Promise)!==null&&t!==void 0?t:Promise}function $h(n){return n&&Pn(n.next)&&Pn(n.error)&&Pn(n.complete)}function Wh(n){return n&&n instanceof ys||$h(n)&&Xu(n)}function Yh(n){return Pn(n?.lift)}function $t(n){return function(t){if(Yh(t))return t.lift(function(e){try{return n(e,this)}catch(r){this.error(r)}});throw new TypeError("Unable to lift unknown Observable type")}}function zt(n,t,e,r,i){return new Xh(n,t,e,r,i)}var Xh=(function(n){$e(t,n);function t(e,r,i,o,s,a){var l=n.call(this,e)||this;return l.onFinalize=s,l.shouldUnsubscribe=a,l._next=r?function(u){try{r(u)}catch(c){e.error(c)}}:n.prototype._next,l._error=o?function(u){try{o(u)}catch(c){e.error(c)}finally{this.unsubscribe()}}:n.prototype._error,l._complete=i?function(){try{i()}catch(u){e.error(u)}finally{this.unsubscribe()}}:n.prototype._complete,l}return t.prototype.unsubscribe=function(){var e;if(!this.shouldUnsubscribe||this.shouldUnsubscribe()){var r=this.closed;n.prototype.unsubscribe.call(this),!r&&((e=this.onFinalize)===null||e===void 0||e.call(this))}},t})(ys),qh=Wu(function(n){return function(){n(this),this.name="ObjectUnsubscribedError",this.message="object unsubscribed"}}),_i=(function(n){$e(t,n);function t(){var e=n.call(this)||this;return e.closed=!1,e.currentObservers=null,e.observers=[],e.isStopped=!1,e.hasError=!1,e.thrownError=null,e}return t.prototype.lift=function(e){var r=new rl(this,this);return r.operator=e,r},t.prototype._throwIfClosed=function(){if(this.closed)throw new qh},t.prototype.next=function(e){var r=this;Wr(function(){var i,o;if(r._throwIfClosed(),!r.isStopped){r.currentObservers||(r.currentObservers=Array.from(r.observers));try{for(var s=Le(r.currentObservers),a=s.next();!a.done;a=s.next()){var l=a.value;l.next(e)}}catch(u){i={error:u}}finally{try{a&&!a.done&&(o=s.return)&&o.call(s)}finally{if(i)throw i.error}}}})},t.prototype.error=function(e){var r=this;Wr(function(){if(r._throwIfClosed(),!r.isStopped){r.hasError=r.isStopped=!0,r.thrownError=e;for(var i=r.observers;i.length;)i.shift().error(e)}})},t.prototype.complete=function(){var e=this;Wr(function(){if(e._throwIfClosed(),!e.isStopped){e.isStopped=!0;for(var r=e.observers;r.length;)r.shift().complete()}})},t.prototype.unsubscribe=function(){this.isStopped=this.closed=!0,this.observers=this.currentObservers=null},Object.defineProperty(t.prototype,"observed",{get:function(){var e;return((e=this.observers)===null||e===void 0?void 0:e.length)>0},enumerable:!1,configurable:!0}),t.prototype._trySubscribe=function(e){return this._throwIfClosed(),n.prototype._trySubscribe.call(this,e)},t.prototype._subscribe=function(e){return this._throwIfClosed(),this._checkFinalizedStatuses(e),this._innerSubscribe(e)},t.prototype._innerSubscribe=function(e){var r=this,i=this,o=i.hasError,s=i.isStopped,a=i.observers;return o||s?Yu:(this.currentObservers=null,a.push(e),new We(function(){r.currentObservers=null,ko(a,e)}))},t.prototype._checkFinalizedStatuses=function(e){var r=this,i=r.hasError,o=r.thrownError,s=r.isStopped;i?e.error(o):s&&e.complete()},t.prototype.asObservable=function(){var e=new Kn;return e.source=this,e},t.create=function(e,r){return new rl(e,r)},t})(Kn),rl=(function(n){$e(t,n);function t(e,r){var i=n.call(this)||this;return i.destination=e,i.source=r,i}return t.prototype.next=function(e){var r,i;(i=(r=this.destination)===null||r===void 0?void 0:r.next)===null||i===void 0||i.call(r,e)},t.prototype.error=function(e){var r,i;(i=(r=this.destination)===null||r===void 0?void 0:r.error)===null||i===void 0||i.call(r,e)},t.prototype.complete=function(){var e,r;(r=(e=this.destination)===null||e===void 0?void 0:e.complete)===null||r===void 0||r.call(e)},t.prototype._subscribe=function(e){var r,i;return(i=(r=this.source)===null||r===void 0?void 0:r.subscribe(e))!==null&&i!==void 0?i:Yu},t})(_i),Qh=(function(n){$e(t,n);function t(e){var r=n.call(this)||this;return r._value=e,r}return Object.defineProperty(t.prototype,"value",{get:function(){return this.getValue()},enumerable:!1,configurable:!0}),t.prototype._subscribe=function(e){var r=n.prototype._subscribe.call(this,e);return!r.closed&&e.next(this._value),r},t.prototype.getValue=function(){var e=this,r=e.hasError,i=e.thrownError,o=e._value;if(r)throw i;return this._throwIfClosed(),o},t.prototype.next=function(e){n.prototype.next.call(this,this._value=e)},t})(_i);function Jh(n){return n&&Pn(n.schedule)}function Kh(n){return n[n.length-1]}function np(n){return Jh(Kh(n))?n.pop():void 0}var Ju=(function(n){return n&&typeof n.length=="number"&&typeof n!="function"});function Ku(n){return Pn(n?.then)}function nc(n){return Pn(n[vs])}function tc(n){return Symbol.asyncIterator&&Pn(n?.[Symbol.asyncIterator])}function ec(n){return new TypeError("You provided "+(n!==null&&typeof n=="object"?"an invalid object":"'"+n+"'")+" where a stream was expected. You can provide an Observable, Promise, ReadableStream, Array, AsyncIterable, or Iterable.")}function tp(){return typeof Symbol!="function"||!Symbol.iterator?"@@iterator":Symbol.iterator}var rc=tp();function ic(n){return Pn(n?.[rc])}function oc(n){return Bh(this,arguments,function(){var e,r,i,o;return $u(this,function(s){switch(s.label){case 0:e=n.getReader(),s.label=1;case 1:s.trys.push([1,,9,10]),s.label=2;case 2:return[4,Oe(e.read())];case 3:return r=s.sent(),i=r.value,o=r.done,o?[4,Oe(void 0)]:[3,5];case 4:return[2,s.sent()];case 5:return[4,Oe(i)];case 6:return[4,s.sent()];case 7:return s.sent(),[3,2];case 8:return[3,10];case 9:return e.releaseLock(),[7];case 10:return[2]}})})}function sc(n){return Pn(n?.getReader)}function we(n){if(n instanceof Kn)return n;if(n!=null){if(nc(n))return ep(n);if(Ju(n))return rp(n);if(Ku(n))return ip(n);if(tc(n))return ac(n);if(ic(n))return op(n);if(sc(n))return sp(n)}throw ec(n)}function ep(n){return new Kn(function(t){var e=n[vs]();if(Pn(e.subscribe))return e.subscribe(t);throw new TypeError("Provided object does not correctly implement Symbol.observable")})}function rp(n){return new Kn(function(t){for(var e=0;e<n.length&&!t.closed;e++)t.next(n[e]);t.complete()})}function ip(n){return new Kn(function(t){n.then(function(e){t.closed||(t.next(e),t.complete())},function(e){return t.error(e)}).then(null,qu)})}function op(n){return new Kn(function(t){var e,r;try{for(var i=Le(n),o=i.next();!o.done;o=i.next()){var s=o.value;if(t.next(s),t.closed)return}}catch(a){e={error:a}}finally{try{o&&!o.done&&(r=i.return)&&r.call(i)}finally{if(e)throw e.error}}t.complete()})}function ac(n){return new Kn(function(t){ap(n,t).catch(function(e){return t.error(e)})})}function sp(n){return ac(oc(n))}function ap(n,t){var e,r,i,o;return Nh(this,void 0,void 0,function(){var s,a;return $u(this,function(l){switch(l.label){case 0:l.trys.push([0,5,6,11]),e=jh(n),l.label=1;case 1:return[4,e.next()];case 2:if(r=l.sent(),!!r.done)return[3,4];if(s=r.value,t.next(s),t.closed)return[2];l.label=3;case 3:return[3,1];case 4:return[3,11];case 5:return a=l.sent(),i={error:a},[3,11];case 6:return l.trys.push([6,,9,10]),r&&!r.done&&(o=e.return)?[4,o.call(e)]:[3,8];case 7:l.sent(),l.label=8;case 8:return[3,10];case 9:if(i)throw i.error;return[7];case 10:return[7];case 11:return t.complete(),[2]}})})}function he(n,t,e,r,i){r===void 0&&(r=0),i===void 0&&(i=!1);var o=t.schedule(function(){e(),i?n.add(this.schedule(null,r)):this.unsubscribe()},r);if(n.add(o),!i)return o}function lc(n,t){return t===void 0&&(t=0),$t(function(e,r){e.subscribe(zt(r,function(i){return he(r,n,function(){return r.next(i)},t)},function(){return he(r,n,function(){return r.complete()},t)},function(i){return he(r,n,function(){return r.error(i)},t)}))})}function uc(n,t){return t===void 0&&(t=0),$t(function(e,r){r.add(n.schedule(function(){return e.subscribe(r)},t))})}function lp(n,t){return we(n).pipe(uc(t),lc(t))}function up(n,t){return we(n).pipe(uc(t),lc(t))}function cp(n,t){return new Kn(function(e){var r=0;return t.schedule(function(){r===n.length?e.complete():(e.next(n[r++]),e.closed||this.schedule())})})}function fp(n,t){return new Kn(function(e){var r;return he(e,t,function(){r=n[rc](),he(e,t,function(){var i,o,s;try{i=r.next(),o=i.value,s=i.done}catch(a){e.error(a);return}s?e.complete():e.next(o)},0,!0)}),function(){return Pn(r?.return)&&r.return()}})}function cc(n,t){if(!n)throw new Error("Iterable cannot be null");return new Kn(function(e){he(e,t,function(){var r=n[Symbol.asyncIterator]();he(e,t,function(){r.next().then(function(i){i.done?e.complete():e.next(i.value)})},0,!0)})})}function dp(n,t){return cc(oc(n),t)}function hp(n,t){if(n!=null){if(nc(n))return lp(n,t);if(Ju(n))return cp(n,t);if(Ku(n))return up(n,t);if(tc(n))return cc(n,t);if(ic(n))return fp(n,t);if(sc(n))return dp(n,t)}throw ec(n)}function pp(n,t){return t?hp(n,t):we(n)}function fc(){for(var n=[],t=0;t<arguments.length;t++)n[t]=arguments[t];var e=np(n);return pp(n,e)}function mp(n,t){var e=Pn(n)?n:function(){return n},r=function(i){return i.error(e())};return new Kn(r)}function Te(n,t){return $t(function(e,r){var i=0;e.subscribe(zt(r,function(o){r.next(n.call(t,o,i++))}))})}function gp(n,t,e,r,i,o,s,a){var l=[],u=0,c=0,f=!1,b=function(){f&&!l.length&&!u&&t.complete()},p=function(A){return u<r?E(A):l.push(A)},E=function(A){u++;var F=!1;we(e(A,c++)).subscribe(zt(t,function(S){t.next(S)},function(){F=!0},void 0,function(){if(F)try{u--;for(var S=function(){var gn=l.shift();s||E(gn)};l.length&&u<r;)S();b()}catch(gn){t.error(gn)}}))};return n.subscribe(zt(t,p,function(){f=!0,b()})),function(){}}function To(n,t,e){return e===void 0&&(e=1/0),Pn(t)?To(function(r,i){return Te(function(o,s){return t(r,o,i,s)})(we(n(r,i)))},e):(typeof t=="number"&&(e=t),$t(function(r,i){return gp(r,i,n,e)}))}function yp(n,t){return $t(function(e,r){var i=0;e.subscribe(zt(r,function(o){return n.call(t,o,i++)&&r.next(o)}))})}function dc(n){return $t(function(t,e){var r=null,i=!1,o;r=t.subscribe(zt(e,void 0,void 0,function(s){o=we(n(s,dc(n)(t))),r?(r.unsubscribe(),r=null,o.subscribe(e)):i=!0})),i&&(r.unsubscribe(),r=null,o.subscribe(e))})}function vp(n,t){return Pn(t)?To(n,t,1):To(n,1)}function wp(n){return $t(function(t,e){try{t.subscribe(e)}finally{e.add(n)}})}function bp(n,t){return $t(function(e,r){var i=null,o=0,s=!1,a=function(){return s&&!i&&r.complete()};e.subscribe(zt(r,function(l){i?.unsubscribe();var u=0,c=o++;we(n(l,c)).subscribe(i=zt(r,function(f){return r.next(t?t(l,f,c,u++):f)},function(){i=null,a()}))},function(){s=!0,a()}))})}function _p(n,t,e){var r=Pn(n)||t||e?{next:n,error:t,complete:e}:n;return r?$t(function(i,o){var s;(s=r.subscribe)===null||s===void 0||s.call(r);var a=!0;i.subscribe(zt(o,function(l){var u;(u=r.next)===null||u===void 0||u.call(r,l),o.next(l)},function(){var l;a=!1,(l=r.complete)===null||l===void 0||l.call(r),o.complete()},function(l){var u;a=!1,(u=r.error)===null||u===void 0||u.call(r,l),o.error(l)},function(){var l,u;a&&((l=r.unsubscribe)===null||l===void 0||l.call(r)),(u=r.finalize)===null||u===void 0||u.call(r)}))}):Qu}let Io;function hc(){return Io}function Qt(n){const t=Io;return Io=n,t}const Dp=Symbol("NotFound");function ws(n){return n===Dp||n?.name==="ɵNotFound"}function Ep(n){const t=X(null);try{return n()}finally{X(t)}}const Cp="https://angular.dev/best-practices/security#preventing-cross-site-scripting-xss";class V extends Error{code;constructor(t,e){super(xp(t,e)),this.code=t}}function kp(n){return`NG0${Math.abs(n)}`}function xp(n,t){return`${kp(n)}${t?": "+t:""}`}const Lr=globalThis;function Cn(n){for(let t in n)if(n[t]===Cn)return t;throw Error("")}function bs(n){if(typeof n=="string")return n;if(Array.isArray(n))return`[${n.map(bs).join(", ")}]`;if(n==null)return""+n;const t=n.overriddenName||n.name;if(t)return`${t}`;const e=n.toString();if(e==null)return""+e;const r=e.indexOf(`
`);return r>=0?e.slice(0,r):e}function il(n,t){return n?t?`${n} ${t}`:n:t||""}const Tp=Cn({__forward_ref__:Cn});function pc(n){return n.__forward_ref__=pc,n}function Jn(n){return Ip(n)?n():n}function Ip(n){return typeof n=="function"&&n.hasOwnProperty(Tp)&&n.__forward_ref__===pc}function fn(n){return{token:n.token,providedIn:n.providedIn||null,factory:n.factory,value:void 0}}function Ap(n){return{providers:n.providers||[],imports:n.imports||[]}}function _s(n){return Sp(n,mc)}function Sp(n,t){return n.hasOwnProperty(t)&&n[t]||null}function Fp(n){const t=n?.[mc]??null;return t||null}function ol(n){return n&&n.hasOwnProperty(sl)?n[sl]:null}const mc=Cn({ɵprov:Cn}),sl=Cn({ɵinj:Cn});class on{_desc;ngMetadataName="InjectionToken";ɵprov;constructor(t,e){this._desc=t,this.ɵprov=void 0,typeof e=="number"?this.__NG_ELEMENT_ID__=e:e!==void 0&&(this.ɵprov=fn({token:this,providedIn:e.providedIn||"root",factory:e.factory}))}get multi(){return this}toString(){return`InjectionToken ${this._desc}`}}function gc(n){return n&&!!n.ɵproviders}const Rp=Cn({ɵcmp:Cn}),Op=Cn({ɵdir:Cn}),Pp=Cn({ɵpipe:Cn}),Mp=Cn({ɵmod:Cn}),al=Cn({ɵfac:Cn}),ar=Cn({__NG_ELEMENT_ID__:Cn}),ll=Cn({__NG_ENV_ID__:Cn});function Lp(n){return Di(n),n[Mp]||null}function hr(n){return Di(n),n[Rp]||null}function yc(n){return Di(n),n[Op]||null}function Np(n){return Di(n),n[Pp]||null}function Di(n,t){if(n==null)throw new V(-919,!1)}function Bp(n){return typeof n=="string"?n:n==null?"":String(n)}const vc=Cn({ngErrorCode:Cn}),jp=Cn({ngErrorMessage:Cn});function wc(n,t){return bc("",-200)}function Ds(n,t){throw new V(-201,!1)}function bc(n,t,e){const r=new V(t,n);return r[vc]=t,r[jp]=n,r}function Hp(n){return n[vc]}let Ao;function _c(){return Ao}function at(n){const t=Ao;return Ao=n,t}function Dc(n,t,e){const r=_s(n);if(r&&r.providedIn=="root")return r.value===void 0?r.value=r.factory():r.value;if(e&8)return null;if(t!==void 0)return t;Ds()}const Up={},pe=Up,zp="__NG_DI_FLAG__";class Vp{injector;constructor(t){this.injector=t}retrieve(t,e){const r=pr(e)||0;try{return this.injector.get(t,r&8?null:pe,r)}catch(i){if(ws(i))return i;throw i}}}function Zp(n,t=0){const e=hc();if(e===void 0)throw new V(-203,!1);if(e===null)return Dc(n,void 0,t);{const r=Gp(t),i=e.retrieve(n,r);if(ws(i)){if(r.optional)return null;throw i}return i}}function cn(n,t=0){return(_c()||Zp)(Jn(n),t)}function z(n,t){return cn(n,pr(t))}function pr(n){return typeof n>"u"||typeof n=="number"?n:0|(n.optional&&8)|(n.host&&1)|(n.self&&2)|(n.skipSelf&&4)}function Gp(n){return{optional:!!(n&8),host:!!(n&1),self:!!(n&2),skipSelf:!!(n&4)}}function So(n){const t=[];for(let e=0;e<n.length;e++){const r=Jn(n[e]);if(Array.isArray(r)){if(r.length===0)throw new V(900,!1);let i,o=0;for(let s=0;s<r.length;s++){const a=r[s],l=$p(a);typeof l=="number"?l===-1?i=a.token:o|=l:i=a}t.push(cn(i,o))}else t.push(cn(r))}return t}function $p(n){return n[zp]}function mr(n,t){const e=n.hasOwnProperty(al);return e?n[al]:null}function Es(n,t){n.forEach(e=>Array.isArray(e)?Es(e,t):t(e))}function Ec(n,t,e){t>=n.length?n.push(e):n.splice(t,0,e)}function ni(n,t){return t>=n.length-1?n.pop():n.splice(t,1)[0]}function Wp(n,t,e,r){let i=n.length;if(i==t)n.push(e,r);else if(i===1)n.push(r,n[0]),n[0]=e;else{for(i--,n.push(n[i-1],n[i]);i>t;){const o=i-2;n[i]=n[o],i--}n[t]=e,n[t+1]=r}}function Yp(n,t,e){let r=Dr(n,t);return r>=0?n[r|1]=e:(r=~r,Wp(n,r,t,e)),r}function $i(n,t){const e=Dr(n,t);if(e>=0)return n[e|1]}function Dr(n,t){return Xp(n,t,1)}function Xp(n,t,e){let r=0,i=n.length>>e;for(;i!==r;){const o=r+(i-r>>1),s=n[o<<e];if(t===s)return o<<e;s>t?i=o:r=o+1}return~(i<<e)}const Ne={},wt=[],Cs=new on(""),ks=new on("",-1),Cc=new on("");class kc{get(t,e=pe){if(e===pe){const i=bc("",-201);throw i.name="ɵNotFound",i}return e}}function qp(n){return{ɵproviders:n}}function Qp(...n){return{ɵproviders:xc(!0,n),ɵfromNgModule:!0}}function xc(n,...t){const e=[],r=new Set;let i;const o=s=>{e.push(s)};return Es(t,s=>{const a=s;Fo(a,o,[],r)&&(i||=[],i.push(a))}),i!==void 0&&Tc(i,o),e}function Tc(n,t){for(let e=0;e<n.length;e++){const{ngModule:r,providers:i}=n[e];xs(i,o=>{t(o,r)})}}function Fo(n,t,e,r){if(n=Jn(n),!n)return!1;let i=null,o=ol(n);const s=!o&&hr(n);if(!o&&!s){const l=n.ngModule;if(o=ol(l),o)i=l;else return!1}else{if(s&&!s.standalone)return!1;i=n}const a=r.has(i);if(s){if(a)return!1;if(r.add(i),s.dependencies){const l=typeof s.dependencies=="function"?s.dependencies():s.dependencies;for(const u of l)Fo(u,t,e,r)}}else if(o){if(o.imports!=null&&!a){r.add(i);let u;Es(o.imports,c=>{Fo(c,t,e,r)&&(u||=[],u.push(c))}),u!==void 0&&Tc(u,t)}if(!a){const u=mr(i)||(()=>new i);t({provide:i,useFactory:u,deps:wt},i),t({provide:Cc,useValue:i,multi:!0},i),t({provide:Cs,useValue:()=>cn(i),multi:!0},i)}const l=o.providers;if(l!=null&&!a){const u=n;xs(l,c=>{t(c,u)})}}else return!1;return i!==n&&n.providers!==void 0}function xs(n,t){for(let e of n)gc(e)&&(e=e.ɵproviders),Array.isArray(e)?xs(e,t):t(e)}const Jp=Cn({provide:String,useValue:Cn});function Ic(n){return n!==null&&typeof n=="object"&&Jp in n}function Kp(n){return!!(n&&n.useExisting)}function n0(n){return!!(n&&n.useFactory)}function Be(n){return typeof n=="function"}function t0(n){return!!n.useClass}const Ts=new on(""),Yr={},ul={};let Wi;function Is(){return Wi===void 0&&(Wi=new kc),Wi}class Vt{}class As extends Vt{parent;source;scopes;records=new Map;_ngOnDestroyHooks=new Set;_onDestroyHooks=[];get destroyed(){return this._destroyed}_destroyed=!1;injectorDefTypes;constructor(t,e,r,i){super(),this.parent=e,this.source=r,this.scopes=i,Oo(t,s=>this.processProvider(s)),this.records.set(ks,Ie(void 0,this)),i.has("environment")&&this.records.set(Vt,Ie(void 0,this));const o=this.records.get(Ts);o!=null&&typeof o.value=="string"&&this.scopes.add(o.value),this.injectorDefTypes=new Set(this.get(Cc,wt,{self:!0}))}retrieve(t,e){const r=pr(e)||0;try{return this.get(t,pe,r)}catch(i){if(ws(i))return i;throw i}}destroy(){ir(this),this._destroyed=!0;const t=X(null);try{for(const r of this._ngOnDestroyHooks)r.ngOnDestroy();const e=this._onDestroyHooks;this._onDestroyHooks=[];for(const r of e)r()}finally{this.records.clear(),this._ngOnDestroyHooks.clear(),this.injectorDefTypes.clear(),X(t)}}onDestroy(t){return ir(this),this._onDestroyHooks.push(t),()=>this.removeOnDestroy(t)}runInContext(t){ir(this);const e=Qt(this),r=at(void 0);try{return t()}finally{Qt(e),at(r)}}get(t,e=pe,r){if(ir(this),t.hasOwnProperty(ll))return t[ll](this);const i=pr(r),o=Qt(this),s=at(void 0);try{if(!(i&4)){let l=this.records.get(t);if(l===void 0){const u=s0(t)&&_s(t);u&&this.injectableDefInScope(u)?l=Ie(Ro(t),Yr):l=null,this.records.set(t,l)}if(l!=null)return this.hydrate(t,l,i)}const a=i&2?Is():this.parent;return e=i&8&&e===pe?null:e,a.get(t,e)}catch(a){const l=Hp(a);throw l===-200||l===-201?new V(l,null):a}finally{at(s),Qt(o)}}resolveInjectorInitializers(){const t=X(null),e=Qt(this),r=at(void 0);try{const i=this.get(Cs,wt,{self:!0});for(const o of i)o()}finally{Qt(e),at(r),X(t)}}toString(){return"R3Injector[...]"}processProvider(t){t=Jn(t);let e=Be(t)?t:Jn(t&&t.provide);const r=r0(t);if(!Be(t)&&t.multi===!0){let i=this.records.get(e);i||(i=Ie(void 0,Yr,!0),i.factory=()=>So(i.multi),this.records.set(e,i)),e=t,i.multi.push(t)}this.records.set(e,r)}hydrate(t,e,r){const i=X(null);try{if(e.value===ul)throw wc("");return e.value===Yr&&(e.value=ul,e.value=e.factory(void 0,r)),typeof e.value=="object"&&e.value&&o0(e.value)&&this._ngOnDestroyHooks.add(e.value),e.value}finally{X(i)}}injectableDefInScope(t){if(!t.providedIn)return!1;const e=Jn(t.providedIn);return typeof e=="string"?e==="any"||this.scopes.has(e):this.injectorDefTypes.has(e)}removeOnDestroy(t){const e=this._onDestroyHooks.indexOf(t);e!==-1&&this._onDestroyHooks.splice(e,1)}}function Ro(n){const t=_s(n),e=t!==null?t.factory:mr(n);if(e!==null)return e;if(n instanceof on)throw new V(-204,!1);if(n instanceof Function)return e0(n);throw new V(-204,!1)}function e0(n){if(n.length>0)throw new V(-204,!1);const e=Fp(n);return e!==null?()=>e.factory(n):()=>new n}function r0(n){if(Ic(n))return Ie(void 0,n.useValue);{const t=Ac(n);return Ie(t,Yr)}}function Ac(n,t,e){let r;if(Be(n)){const i=Jn(n);return mr(i)||Ro(i)}else if(Ic(n))r=()=>Jn(n.useValue);else if(n0(n))r=()=>n.useFactory(...So(n.deps||[]));else if(Kp(n))r=(i,o)=>cn(Jn(n.useExisting),o!==void 0&&o&8?8:void 0);else{const i=Jn(n&&(n.useClass||n.provide));if(i0(n))r=()=>new i(...So(n.deps));else return mr(i)||Ro(i)}return r}function ir(n){if(n.destroyed)throw new V(-205,!1)}function Ie(n,t,e=!1){return{factory:n,value:t,multi:e?[]:void 0}}function i0(n){return!!n.deps}function o0(n){return n!==null&&typeof n=="object"&&typeof n.ngOnDestroy=="function"}function s0(n){return typeof n=="function"||typeof n=="object"&&n.ngMetadataName==="InjectionToken"}function Oo(n,t){for(const e of n)Array.isArray(e)?Oo(e,t):e&&gc(e)?Oo(e.ɵproviders,t):t(e)}function Ss(n,t){let e;n instanceof As?(ir(n),e=n):e=new Vp(n);const r=Qt(e),i=at(void 0);try{return t()}finally{Qt(r),at(i)}}function a0(){return _c()!==void 0||hc()!=null}const Wt=0,Y=1,q=2,$n=3,_t=4,Et=5,gr=6,ti=7,Zn=8,re=9,ie=10,zn=11,yr=12,cl=13,Ye=14,It=15,je=16,Ae=17,He=18,Zt=19,Sc=20,ne=21,Yi=22,ei=23,ut=24,Xi=25,Gt=26,qn=27,Fc=1,fl=6,me=7,ri=8,ii=9,jn=10;function te(n){return Array.isArray(n)&&typeof n[Fc]=="object"}function Ft(n){return Array.isArray(n)&&n[Fc]===!0}function Rc(n){return(n.flags&4)!==0}function be(n){return n.componentOffset>-1}function Fs(n){return(n.flags&1)===1}function _e(n){return!!n.template}function oi(n){return(n[q]&512)!==0}function Xe(n){return(n[q]&256)===256}const l0="svg",u0="math";function At(n){for(;Array.isArray(n);)n=n[Wt];return n}function Oc(n,t){return At(t[n])}function Yt(n,t){return At(t[n.index])}function Rs(n,t){return n.data[t]}function Dt(n,t){const e=t[n];return te(e)?e:e[Wt]}function Os(n){return(n[q]&128)===128}function c0(n){return Ft(n[$n])}function oe(n,t){return t==null?null:n[t]}function Pc(n){n[Ae]=0}function Mc(n){n[q]&1024||(n[q]|=1024,Os(n)&&Ei(n))}function f0(n,t){for(;n>0;)t=t[Ye],n--;return t}function si(n){return!!(n[q]&9216||n[ut]?.dirty)}function Po(n){n[ie].changeDetectionScheduler?.notify(8),n[q]&64&&(n[q]|=1024),si(n)&&Ei(n)}function Ei(n){n[ie].changeDetectionScheduler?.notify(0);let t=ge(n);for(;t!==null&&!(t[q]&8192||(t[q]|=8192,!Os(t)));)t=ge(t)}function Lc(n,t){if(Xe(n))throw new V(911,!1);n[ne]===null&&(n[ne]=[]),n[ne].push(t)}function d0(n,t){if(n[ne]===null)return;const e=n[ne].indexOf(t);e!==-1&&n[ne].splice(e,1)}function ge(n){const t=n[$n];return Ft(t)?t[$n]:t}function h0(n){return n[ti]??=[]}function p0(n){return n.cleanup??=[]}const ln={lFrame:$c(null),bindingsEnabled:!0,skipHydrationRootTNode:null};let Mo=!1;function m0(){return ln.lFrame.elementDepthCount}function g0(){ln.lFrame.elementDepthCount++}function Nc(){ln.lFrame.elementDepthCount--}function y0(){return ln.bindingsEnabled}function v0(){return ln.skipHydrationRootTNode!==null}function Bc(n){return ln.skipHydrationRootTNode===n}function jc(){ln.skipHydrationRootTNode=null}function Fn(){return ln.lFrame.lView}function Ct(){return ln.lFrame.tView}function ft(){let n=Hc();for(;n!==null&&n.type===64;)n=n.parent;return n}function Hc(){return ln.lFrame.currentTNode}function w0(){const n=ln.lFrame,t=n.currentTNode;return n.isParent?t:t.parent}function Er(n,t){const e=ln.lFrame;e.currentTNode=n,e.isParent=t}function Uc(){return ln.lFrame.isParent}function b0(){ln.lFrame.isParent=!1}function zc(){return Mo}function dl(n){const t=Mo;return Mo=n,t}function _0(n){return ln.lFrame.bindingIndex=n}function Ci(){return ln.lFrame.bindingIndex++}function D0(n){const t=ln.lFrame,e=t.bindingIndex;return t.bindingIndex=t.bindingIndex+n,e}function E0(){return ln.lFrame.inI18n}function C0(n,t){const e=ln.lFrame;e.bindingIndex=e.bindingRootIndex=n,Lo(t)}function k0(){return ln.lFrame.currentDirectiveIndex}function Lo(n){ln.lFrame.currentDirectiveIndex=n}function x0(n){const t=ln.lFrame.currentDirectiveIndex;return t===-1?null:n[t]}function Vc(n){ln.lFrame.currentQueryIndex=n}function T0(n){const t=n[Y];return t.type===2?t.declTNode:t.type===1?n[Et]:null}function Zc(n,t,e){if(e&4){let i=t,o=n;for(;i=i.parent,i===null&&!(e&1);)if(i=T0(o),i===null||(o=o[Ye],i.type&10))break;if(i===null)return!1;t=i,n=o}const r=ln.lFrame=Gc();return r.currentTNode=t,r.lView=n,!0}function Ps(n){const t=Gc(),e=n[Y];ln.lFrame=t,t.currentTNode=e.firstChild,t.lView=n,t.tView=e,t.contextLView=n,t.bindingIndex=e.bindingStartIndex,t.inI18n=!1}function Gc(){const n=ln.lFrame,t=n===null?null:n.child;return t===null?$c(n):t}function $c(n){const t={currentTNode:null,isParent:!0,lView:null,tView:null,selectedIndex:-1,contextLView:null,elementDepthCount:0,currentNamespace:null,currentDirectiveIndex:-1,bindingRootIndex:-1,bindingIndex:-1,currentQueryIndex:0,parent:n,child:null,inI18n:!1};return n!==null&&(n.child=t),t}function Wc(){const n=ln.lFrame;return ln.lFrame=n.parent,n.currentTNode=null,n.lView=null,n}const Yc=Wc;function Ms(){const n=Wc();n.isParent=!0,n.tView=null,n.selectedIndex=-1,n.contextLView=null,n.elementDepthCount=0,n.currentDirectiveIndex=-1,n.currentNamespace=null,n.bindingRootIndex=-1,n.bindingIndex=-1,n.currentQueryIndex=0}function I0(n){return(ln.lFrame.contextLView=f0(n,ln.lFrame.contextLView))[Zn]}function De(){return ln.lFrame.selectedIndex}function ye(n){ln.lFrame.selectedIndex=n}function A0(){const n=ln.lFrame;return Rs(n.tView,n.selectedIndex)}function S0(){return ln.lFrame.currentNamespace}let Xc=!0;function Ls(){return Xc}function Ns(n){Xc=n}function hl(n,t=null,e=null,r){const i=qc(n,t,e);return i.resolveInjectorInitializers(),i}function qc(n,t=null,e=null,r,i=new Set){const o=[e||wt,Qp(n)];return new As(o,t||Is(),null,i)}class qe{static THROW_IF_NOT_FOUND=pe;static NULL=new kc;static create(t,e){if(Array.isArray(t))return hl({name:""},e,t);{const r=t.name??"";return hl({name:r},t.parent,t.providers)}}static ɵprov=fn({token:qe,providedIn:"any",factory:()=>cn(ks)});static __NG_ELEMENT_ID__=-1}const se=new on("");let Bs=(()=>{class n{static __NG_ELEMENT_ID__=R0;static __NG_ENV_ID__=e=>e}return n})();class F0 extends Bs{_lView;constructor(t){super(),this._lView=t}get destroyed(){return Xe(this._lView)}onDestroy(t){const e=this._lView;return Lc(e,t),()=>d0(e,t)}}function R0(){return new F0(Fn())}const O0=!1,P0=new on("");let Cr=(()=>{class n{taskId=0;pendingTasks=new Set;destroyed=!1;pendingTask=new Qh(!1);debugTaskTracker=z(P0,{optional:!0});get hasPendingTasks(){return this.destroyed?!1:this.pendingTask.value}get hasPendingTasksObservable(){return this.destroyed?new Kn(e=>{e.next(!1),e.complete()}):this.pendingTask}add(){!this.hasPendingTasks&&!this.destroyed&&this.pendingTask.next(!0);const e=this.taskId++;return this.pendingTasks.add(e),this.debugTaskTracker?.add(e),e}has(e){return this.pendingTasks.has(e)}remove(e){this.pendingTasks.delete(e),this.debugTaskTracker?.remove(e),this.pendingTasks.size===0&&this.hasPendingTasks&&this.pendingTask.next(!1)}ngOnDestroy(){this.pendingTasks.clear(),this.hasPendingTasks&&this.pendingTask.next(!1),this.destroyed=!0,this.pendingTask.unsubscribe()}static ɵprov=fn({token:n,providedIn:"root",factory:()=>new n})}return n})();class M0 extends _i{__isAsync;destroyRef=void 0;pendingTasks=void 0;constructor(t=!1){super(),this.__isAsync=t,a0()&&(this.destroyRef=z(Bs,{optional:!0})??void 0,this.pendingTasks=z(Cr,{optional:!0})??void 0)}emit(t){const e=X(null);try{super.next(t)}finally{X(e)}}subscribe(t,e,r){let i=t,o=e||(()=>null),s=r;if(t&&typeof t=="object"){const l=t;i=l.next?.bind(l),o=l.error?.bind(l),s=l.complete?.bind(l)}this.__isAsync&&(o=this.wrapInTimeout(o),i&&(i=this.wrapInTimeout(i)),s&&(s=this.wrapInTimeout(s)));const a=super.subscribe({next:i,error:o,complete:s});return t instanceof We&&t.add(a),a}wrapInTimeout(t){return e=>{const r=this.pendingTasks?.add();setTimeout(()=>{try{t(e)}finally{r!==void 0&&this.pendingTasks?.remove(r)}})}}}const Jt=M0;function ai(...n){}function Qc(n){let t,e;function r(){n=ai;try{e!==void 0&&typeof cancelAnimationFrame=="function"&&cancelAnimationFrame(e),t!==void 0&&clearTimeout(t)}catch{}}return t=setTimeout(()=>{n(),r()}),typeof requestAnimationFrame=="function"&&(e=requestAnimationFrame(()=>{n(),r()})),()=>r()}function L0(n){return queueMicrotask(()=>n()),()=>{n=ai}}const js="isAngularZone",li=js+"_ID";let N0=0;class St{hasPendingMacrotasks=!1;hasPendingMicrotasks=!1;isStable=!0;onUnstable=new Jt(!1);onMicrotaskEmpty=new Jt(!1);onStable=new Jt(!1);onError=new Jt(!1);constructor(t){const{enableLongStackTrace:e=!1,shouldCoalesceEventChangeDetection:r=!1,shouldCoalesceRunChangeDetection:i=!1,scheduleInRootZone:o=O0}=t;if(typeof Zone>"u")throw new V(908,!1);Zone.assertZonePatched();const s=this;s._nesting=0,s._outer=s._inner=Zone.current,Zone.TaskTrackingZoneSpec&&(s._inner=s._inner.fork(new Zone.TaskTrackingZoneSpec)),e&&Zone.longStackTraceZoneSpec&&(s._inner=s._inner.fork(Zone.longStackTraceZoneSpec)),s.shouldCoalesceEventChangeDetection=!i&&r,s.shouldCoalesceRunChangeDetection=i,s.callbackScheduled=!1,s.scheduleInRootZone=o,H0(s)}static isInAngularZone(){return typeof Zone<"u"&&Zone.current.get(js)===!0}static assertInAngularZone(){if(!St.isInAngularZone())throw new V(909,!1)}static assertNotInAngularZone(){if(St.isInAngularZone())throw new V(909,!1)}run(t,e,r){return this._inner.run(t,e,r)}runTask(t,e,r,i){const o=this._inner,s=o.scheduleEventTask("NgZoneEvent: "+i,t,B0,ai,ai);try{return o.runTask(s,e,r)}finally{o.cancelTask(s)}}runGuarded(t,e,r){return this._inner.runGuarded(t,e,r)}runOutsideAngular(t){return this._outer.run(t)}}const B0={};function Hs(n){if(n._nesting==0&&!n.hasPendingMicrotasks&&!n.isStable)try{n._nesting++,n.onMicrotaskEmpty.emit(null)}finally{if(n._nesting--,!n.hasPendingMicrotasks)try{n.runOutsideAngular(()=>n.onStable.emit(null))}finally{n.isStable=!0}}}function j0(n){if(n.isCheckStableRunning||n.callbackScheduled)return;n.callbackScheduled=!0;function t(){Qc(()=>{n.callbackScheduled=!1,No(n),n.isCheckStableRunning=!0,Hs(n),n.isCheckStableRunning=!1})}n.scheduleInRootZone?Zone.root.run(()=>{t()}):n._outer.run(()=>{t()}),No(n)}function H0(n){const t=()=>{j0(n)},e=N0++;n._inner=n._inner.fork({name:"angular",properties:{[js]:!0,[li]:e,[li+e]:!0},onInvokeTask:(r,i,o,s,a,l)=>{if(z0(l))return r.invokeTask(o,s,a,l);try{return pl(n),r.invokeTask(o,s,a,l)}finally{(n.shouldCoalesceEventChangeDetection&&s.type==="eventTask"||n.shouldCoalesceRunChangeDetection)&&t(),ml(n)}},onInvoke:(r,i,o,s,a,l,u)=>{try{return pl(n),r.invoke(o,s,a,l,u)}finally{n.shouldCoalesceRunChangeDetection&&!n.callbackScheduled&&!V0(l)&&t(),ml(n)}},onHasTask:(r,i,o,s)=>{r.hasTask(o,s),i===o&&(s.change=="microTask"?(n._hasPendingMicrotasks=s.microTask,No(n),Hs(n)):s.change=="macroTask"&&(n.hasPendingMacrotasks=s.macroTask))},onHandleError:(r,i,o,s)=>(r.handleError(o,s),n.runOutsideAngular(()=>n.onError.emit(s)),!1)})}function No(n){n._hasPendingMicrotasks||(n.shouldCoalesceEventChangeDetection||n.shouldCoalesceRunChangeDetection)&&n.callbackScheduled===!0?n.hasPendingMicrotasks=!0:n.hasPendingMicrotasks=!1}function pl(n){n._nesting++,n.isStable&&(n.isStable=!1,n.onUnstable.emit(null))}function ml(n){n._nesting--,Hs(n)}class U0{hasPendingMicrotasks=!1;hasPendingMacrotasks=!1;isStable=!0;onUnstable=new Jt;onMicrotaskEmpty=new Jt;onStable=new Jt;onError=new Jt;run(t,e,r){return t.apply(e,r)}runGuarded(t,e,r){return t.apply(e,r)}runOutsideAngular(t){return t()}runTask(t,e,r,i){return t.apply(e,r)}}function z0(n){return Jc(n,"__ignore_ng_zone__")}function V0(n){return Jc(n,"__scheduler_tick__")}function Jc(n,t){return!Array.isArray(n)||n.length!==1?!1:n[0]?.data?.[t]===!0}class ki{_console=console;handleError(t){this._console.error("ERROR",t)}}const Qe=new on("",{factory:()=>{const n=z(St),t=z(Vt);let e;return r=>{n.runOutsideAngular(()=>{t.destroyed&&!e?setTimeout(()=>{throw r}):(e??=t.get(ki),e.handleError(r))})}}}),Z0={provide:Cs,useValue:()=>{z(ki,{optional:!0})},multi:!0};function jt(n,t){const[e,r,i]=Rh(n,t?.equal),o=e;return o[dr],o.set=r,o.update=i,o.asReadonly=G0.bind(o),o}function G0(){const n=this[dr];if(n.readonlyFn===void 0){const t=()=>this();t[dr]=n,n.readonlyFn=t}return n.readonlyFn}class Us{}const zs=new on("",{factory:()=>!0}),$0=new on("");let W0=(()=>{class n{internalPendingTasks=z(Cr);scheduler=z(Us);errorHandler=z(Qe);add(){const e=this.internalPendingTasks.add();return()=>{this.internalPendingTasks.has(e)&&(this.scheduler.notify(11),this.internalPendingTasks.remove(e))}}run(e){const r=this.add();e().catch(this.errorHandler).finally(r)}static ɵprov=fn({token:n,providedIn:"root",factory:()=>new n})}return n})(),Y0=(()=>{class n{static ɵprov=fn({token:n,providedIn:"root",factory:()=>new X0})}return n})();class X0{dirtyEffectCount=0;queues=new Map;add(t){this.enqueue(t),this.schedule(t)}schedule(t){t.dirty&&this.dirtyEffectCount++}remove(t){const e=t.zone,r=this.queues.get(e);r.has(t)&&(r.delete(t),t.dirty&&this.dirtyEffectCount--)}enqueue(t){const e=t.zone;this.queues.has(e)||this.queues.set(e,new Set);const r=this.queues.get(e);r.has(t)||r.add(t)}flush(){for(;this.dirtyEffectCount>0;){let t=!1;for(const[e,r]of this.queues)e===null?t||=this.flushQueue(r):t||=e.run(()=>this.flushQueue(r));t||(this.dirtyEffectCount=0)}}flushQueue(t){let e=!1;for(const r of t)r.dirty&&(this.dirtyEffectCount--,e=!0,r.run());return e}}function Vs(n){return{toString:n}.toString()}function q0(n){return typeof n=="function"}function Kc(n,t,e,r){t!==null?t.applyValueToInputSignal(t,r):n[e]=r}class Q0{previousValue;currentValue;firstChange;constructor(t,e,r){this.previousValue=t,this.currentValue=e,this.firstChange=r}isFirstChange(){return this.firstChange}}const nf=(()=>{const n=()=>tf;return n.ngInherit=!0,n})();function tf(n){return n.type.prototype.ngOnChanges&&(n.setInput=K0),J0}function J0(){const n=rf(this),t=n?.current;if(t){const e=n.previous;if(e===Ne)n.previous=t;else for(let r in t)e[r]=t[r];n.current=null,this.ngOnChanges(t)}}function K0(n,t,e,r,i){const o=this.declaredInputs[r],s=rf(n)||nm(n,{previous:Ne,current:null}),a=s.current||(s.current={}),l=s.previous,u=l[o];a[o]=new Q0(u&&u.currentValue,e,l===Ne),Kc(n,t,i,e)}const ef="__ngSimpleChanges__";function rf(n){return n[ef]||null}function nm(n,t){return n[ef]=t}const gl=[],En=function(n,t=null,e){for(let r=0;r<gl.length;r++){const i=gl[r];i(n,t,e)}};var mn=(function(n){return n[n.TemplateCreateStart=0]="TemplateCreateStart",n[n.TemplateCreateEnd=1]="TemplateCreateEnd",n[n.TemplateUpdateStart=2]="TemplateUpdateStart",n[n.TemplateUpdateEnd=3]="TemplateUpdateEnd",n[n.LifecycleHookStart=4]="LifecycleHookStart",n[n.LifecycleHookEnd=5]="LifecycleHookEnd",n[n.OutputStart=6]="OutputStart",n[n.OutputEnd=7]="OutputEnd",n[n.BootstrapApplicationStart=8]="BootstrapApplicationStart",n[n.BootstrapApplicationEnd=9]="BootstrapApplicationEnd",n[n.BootstrapComponentStart=10]="BootstrapComponentStart",n[n.BootstrapComponentEnd=11]="BootstrapComponentEnd",n[n.ChangeDetectionStart=12]="ChangeDetectionStart",n[n.ChangeDetectionEnd=13]="ChangeDetectionEnd",n[n.ChangeDetectionSyncStart=14]="ChangeDetectionSyncStart",n[n.ChangeDetectionSyncEnd=15]="ChangeDetectionSyncEnd",n[n.AfterRenderHooksStart=16]="AfterRenderHooksStart",n[n.AfterRenderHooksEnd=17]="AfterRenderHooksEnd",n[n.ComponentStart=18]="ComponentStart",n[n.ComponentEnd=19]="ComponentEnd",n[n.DeferBlockStateStart=20]="DeferBlockStateStart",n[n.DeferBlockStateEnd=21]="DeferBlockStateEnd",n[n.DynamicComponentStart=22]="DynamicComponentStart",n[n.DynamicComponentEnd=23]="DynamicComponentEnd",n[n.HostBindingsUpdateStart=24]="HostBindingsUpdateStart",n[n.HostBindingsUpdateEnd=25]="HostBindingsUpdateEnd",n})(mn||{});function tm(n,t,e){const{ngOnChanges:r,ngOnInit:i,ngDoCheck:o}=t.type.prototype;if(r){const s=tf(t);(e.preOrderHooks??=[]).push(n,s),(e.preOrderCheckHooks??=[]).push(n,s)}i&&(e.preOrderHooks??=[]).push(0-n,i),o&&((e.preOrderHooks??=[]).push(n,o),(e.preOrderCheckHooks??=[]).push(n,o))}function em(n,t){for(let e=t.directiveStart,r=t.directiveEnd;e<r;e++){const o=n.data[e].type.prototype,{ngAfterContentInit:s,ngAfterContentChecked:a,ngAfterViewInit:l,ngAfterViewChecked:u,ngOnDestroy:c}=o;s&&(n.contentHooks??=[]).push(-e,s),a&&((n.contentHooks??=[]).push(e,a),(n.contentCheckHooks??=[]).push(e,a)),l&&(n.viewHooks??=[]).push(-e,l),u&&((n.viewHooks??=[]).push(e,u),(n.viewCheckHooks??=[]).push(e,u)),c!=null&&(n.destroyHooks??=[]).push(e,c)}}function Xr(n,t,e){of(n,t,3,e)}function qr(n,t,e,r){(n[q]&3)===e&&of(n,t,e,r)}function qi(n,t){let e=n[q];(e&3)===t&&(e&=16383,e+=1,n[q]=e)}function of(n,t,e,r){const i=r!==void 0?n[Ae]&65535:0,o=r??-1,s=t.length-1;let a=0;for(let l=i;l<s;l++)if(typeof t[l+1]=="number"){if(a=t[l],r!=null&&a>=r)break}else t[l]<0&&(n[Ae]+=65536),(a<o||o==-1)&&(rm(n,e,t,l),n[Ae]=(n[Ae]&4294901760)+l+2),l++}function yl(n,t){En(mn.LifecycleHookStart,n,t);const e=X(null);try{t.call(n)}finally{X(e),En(mn.LifecycleHookEnd,n,t)}}function rm(n,t,e,r){const i=e[r]<0,o=e[r+1],s=i?-e[r]:e[r],a=n[s];i?n[q]>>14<n[Ae]>>16&&(n[q]&3)===t&&(n[q]+=16384,yl(a,o)):yl(a,o)}const Pe=-1;class kr{factory;name;injectImpl;resolving=!1;canSeeViewProviders;multi;componentProviders;index;providerFactory;constructor(t,e,r,i){this.factory=t,this.name=i,this.canSeeViewProviders=e,this.injectImpl=r}}function im(n){return(n.flags&8)!==0}function om(n){return(n.flags&16)!==0}function sm(n,t,e){let r=0;for(;r<e.length;){const i=e[r];if(typeof i=="number"){if(i!==0)break;r++;const o=e[r++],s=e[r++],a=e[r++];n.setAttribute(t,s,a,o)}else{const o=i,s=e[++r];am(o)?n.setProperty(t,o,s):n.setAttribute(t,o,s),r++}}return r}function sf(n){return n===3||n===4||n===6}function am(n){return n.charCodeAt(0)===64}function xi(n,t){if(!(t===null||t.length===0))if(n===null||n.length===0)n=t.slice();else{let e=-1;for(let r=0;r<t.length;r++){const i=t[r];typeof i=="number"?e=i:e===0||(e===-1||e===2?vl(n,e,i,null,t[++r]):vl(n,e,i,null,null))}}return n}function vl(n,t,e,r,i){let o=0,s=n.length;if(t===-1)s=-1;else for(;o<n.length;){const a=n[o++];if(typeof a=="number"){if(a===t){s=-1;break}else if(a>t){s=o-1;break}}}for(;o<n.length;){const a=n[o];if(typeof a=="number")break;if(a===e){i!==null&&(n[o+1]=i);return}o++,i!==null&&o++}s!==-1&&(n.splice(s,0,t),o=s+1),n.splice(o++,0,e),i!==null&&n.splice(o++,0,i)}function af(n){return n!==Pe}function ui(n){return n&32767}function lm(n){return n>>16}function ci(n,t){let e=lm(n),r=t;for(;e>0;)r=r[Ye],e--;return r}let Bo=!0;function wl(n){const t=Bo;return Bo=n,t}const um=256,lf=um-1,uf=5;let cm=0;const Tt={};function fm(n,t,e){let r;typeof e=="string"?r=e.charCodeAt(0)||0:e.hasOwnProperty(ar)&&(r=e[ar]),r==null&&(r=e[ar]=cm++);const i=r&lf,o=1<<i;t.data[n+(i>>uf)]|=o}function fi(n,t){const e=cf(n,t);if(e!==-1)return e;const r=t[Y];r.firstCreatePass&&(n.injectorIndex=t.length,Qi(r.data,n),Qi(t,null),Qi(r.blueprint,null));const i=Zs(n,t),o=n.injectorIndex;if(af(i)){const s=ui(i),a=ci(i,t),l=a[Y].data;for(let u=0;u<8;u++)t[o+u]=a[s+u]|l[s+u]}return t[o+8]=i,o}function Qi(n,t){n.push(0,0,0,0,0,0,0,0,t)}function cf(n,t){return n.injectorIndex===-1||n.parent&&n.parent.injectorIndex===n.injectorIndex||t[n.injectorIndex+8]===null?-1:n.injectorIndex}function Zs(n,t){if(n.parent&&n.parent.injectorIndex!==-1)return n.parent.injectorIndex;let e=0,r=null,i=t;for(;i!==null;){if(r=mf(i),r===null)return Pe;if(e++,i=i[Ye],r.injectorIndex!==-1)return r.injectorIndex|e<<16}return Pe}function jo(n,t,e){fm(n,t,e)}function dm(n,t){const e=n.attrs;if(e){const r=e.length;let i=0;for(;i<r;){const o=e[i];if(sf(o))break;if(o===0)i=i+2;else if(typeof o=="number")for(i++;i<r&&typeof e[i]=="string";)i++;else{if(o===t)return e[i+1];i=i+2}}}return null}function ff(n,t,e){if(e&8||n!==void 0)return n;Ds()}function df(n,t,e,r){if(e&8&&r===void 0&&(r=null),(e&3)===0){const i=n[re],o=at(void 0);try{return i?i.get(t,r,e&8):Dc(t,r,e&8)}finally{at(o)}}return ff(r,t,e)}function hf(n,t,e,r=0,i){if(n!==null){if(t[q]&2048&&!(r&2)){const s=ym(n,t,e,r,Tt);if(s!==Tt)return s}const o=pf(n,t,e,r,Tt);if(o!==Tt)return o}return df(t,e,r,i)}function pf(n,t,e,r,i){const o=mm(e);if(typeof o=="function"){if(!Zc(t,n,r))return r&1?ff(i,e,r):df(t,e,r,i);try{let s;if(s=o(r),s==null&&!(r&8))Ds(e);else return s}finally{Yc()}}else if(typeof o=="number"){let s=null,a=cf(n,t),l=Pe,u=r&1?t[It][Et]:null;for((a===-1||r&4)&&(l=a===-1?Zs(n,t):t[a+8],l===Pe||!_l(r,!1)?a=-1:(s=t[Y],a=ui(l),t=ci(l,t)));a!==-1;){const c=t[Y];if(bl(o,a,c.data)){const f=hm(a,t,e,s,r,u);if(f!==Tt)return f}l=t[a+8],l!==Pe&&_l(r,t[Y].data[a+8]===u)&&bl(o,a,t)?(s=c,a=ui(l),t=ci(l,t)):a=-1}}return i}function hm(n,t,e,r,i,o){const s=t[Y],a=s.data[n+8],l=r==null?be(a)&&Bo:r!=s&&(a.type&3)!==0,u=i&1&&o===a,c=pm(a,s,e,l,u);return c!==null?Ho(t,s,c,a,i):Tt}function pm(n,t,e,r,i){const o=n.providerIndexes,s=t.data,a=o&1048575,l=n.directiveStart,u=n.directiveEnd,c=o>>20,f=r?a:a+c,b=i?a+c:u;for(let p=f;p<b;p++){const E=s[p];if(p<l&&e===E||p>=l&&E.type===e)return p}if(i){const p=s[l];if(p&&_e(p)&&p.type===e)return l}return null}function Ho(n,t,e,r,i){let o=n[e];const s=t.data;if(o instanceof kr){const a=o;if(a.resolving)throw wc();const l=wl(a.canSeeViewProviders);a.resolving=!0,s[e].type||s[e];const u=a.injectImpl?at(a.injectImpl):null;Zc(n,r,0);try{o=n[e]=a.factory(void 0,i,s,n,r),t.firstCreatePass&&e>=r.directiveStart&&tm(e,s[e],t)}finally{u!==null&&at(u),wl(l),a.resolving=!1,Yc()}}return o}function mm(n){if(typeof n=="string")return n.charCodeAt(0)||0;const t=n.hasOwnProperty(ar)?n[ar]:void 0;return typeof t=="number"?t>=0?t&lf:gm:t}function bl(n,t,e){const r=1<<n;return!!(e[t+(n>>uf)]&r)}function _l(n,t){return!(n&2)&&!(n&1&&t)}class lr{_tNode;_lView;constructor(t,e){this._tNode=t,this._lView=e}get(t,e,r){return hf(this._tNode,this._lView,t,pr(r),e)}}function gm(){return new lr(ft(),Fn())}function ym(n,t,e,r,i){let o=n,s=t;for(;o!==null&&s!==null&&s[q]&2048&&!oi(s);){const a=pf(o,s,e,r|2,Tt);if(a!==Tt)return a;let l=o.parent;if(!l){const u=s[Sc];if(u){const c=u.get(e,Tt,r&-5);if(c!==Tt)return c}l=mf(s),s=s[Ye]}o=l}return i}function mf(n){const t=n[Y],e=t.type;return e===2?t.declTNode:e===1?n[Et]:null}function vm(n){return dm(ft(),n)}function wm(){return Ti(ft(),Fn())}function Ti(n,t){return new Gs(Yt(n,t))}let Gs=(()=>{class n{nativeElement;constructor(e){this.nativeElement=e}static __NG_ELEMENT_ID__=wm}return n})();function gf(n){return(n.flags&128)===128}var yf=(function(n){return n[n.OnPush=0]="OnPush",n[n.Eager=1]="Eager",n[n.Default=1]="Default",n})(yf||{});const vf=new Map;let bm=0;function _m(){return bm++}function Dm(n){vf.set(n[Zt],n)}function Uo(n){vf.delete(n[Zt])}const Dl="__ngContext__";function Ue(n,t){te(t)?(n[Dl]=t[Zt],Dm(t)):n[Dl]=t}function wf(n){return _f(n[yr])}function bf(n){return _f(n[_t])}function _f(n){for(;n!==null&&!Ft(n);)n=n[_t];return n}let Em;function Cm(n){Em=n}const Df=new on("",{factory:()=>km}),km="ng";const Ef=new on(""),Cf=new on("",{providedIn:"platform",factory:()=>"unknown"}),kf=new on("",{factory:()=>z(se).body?.querySelector("[ngCspNonce]")?.getAttribute("ngCspNonce")||null});const xm="r",Tm="di",xf=!1,Im=new on("",{factory:()=>xf});function Tf(n){return(n.flags&32)===32}let Am=()=>null;function If(n,t,e=!1){return Am()}function Af(n,t){const e=n.contentQueries;if(e!==null){const r=X(null);try{for(let i=0;i<e.length;i+=2){const o=e[i],s=e[i+1];if(s!==-1){const a=n.data[s];Vc(o),a.contentQueries(2,t[s],s)}}}finally{X(r)}}}function zo(n,t,e){Vc(0);const r=X(null);try{t(n,e)}finally{X(r)}}function Sf(n,t,e){if(Rc(t)){const r=X(null);try{const i=t.directiveStart,o=t.directiveEnd;for(let s=i;s<o;s++){const a=n.data[s];if(a.contentQueries){const l=e[s];a.contentQueries(1,l,s)}}}finally{X(r)}}}var Ht=(function(n){return n[n.Emulated=0]="Emulated",n[n.None=2]="None",n[n.ShadowDom=3]="ShadowDom",n[n.ExperimentalIsolatedShadowDom=4]="ExperimentalIsolatedShadowDom",n})(Ht||{});class Sm{changingThisBreaksApplicationSecurity;constructor(t){this.changingThisBreaksApplicationSecurity=t}toString(){return`SafeValue must use [property]=binding: ${this.changingThisBreaksApplicationSecurity} (see ${Cp})`}}function Fm(n){return n instanceof Sm?n.changingThisBreaksApplicationSecurity:n}function Rm(n,t){return n.createText(t)}function Om(n,t,e){n.setValue(t,e)}function Ff(n,t,e){return n.createElement(t,e)}function di(n,t,e,r,i){n.insertBefore(t,e,r,i)}function Rf(n,t,e){n.appendChild(t,e)}function El(n,t,e,r,i){r!==null?di(n,t,e,r,i):Rf(n,t,e)}function Of(n,t,e,r){n.removeChild(null,t,e,r)}function Pm(n,t,e){n.setAttribute(t,"style",e)}function Mm(n,t,e){e===""?n.removeAttribute(t,"class"):n.setAttribute(t,"class",e)}function Pf(n,t,e){const{mergedAttrs:r,classes:i,styles:o}=e;r!==null&&sm(n,t,r),i!==null&&Mm(n,t,i),o!==null&&Pm(n,t,o)}function Lm(n){return n instanceof Function?n():n}function Nm(n,t,e){let r=n.length;for(;;){const i=n.indexOf(t,e);if(i===-1)return i;if(i===0||n.charCodeAt(i-1)<=32){const o=t.length;if(i+o===r||n.charCodeAt(i+o)<=32)return i}e=i+1}}const Mf="ng-template";function Bm(n,t,e,r){let i=0;if(r){for(;i<t.length&&typeof t[i]=="string";i+=2)if(t[i]==="class"&&Nm(t[i+1].toLowerCase(),e,0)!==-1)return!0}else if($s(n))return!1;if(i=t.indexOf(1,i),i>-1){let o;for(;++i<t.length&&typeof(o=t[i])=="string";)if(o.toLowerCase()===e)return!0}return!1}function $s(n){return n.type===4&&n.value!==Mf}function jm(n,t,e){const r=n.type===4&&!e?Mf:n.value;return t===r}function Hm(n,t,e){let r=4;const i=n.attrs,o=i!==null?Vm(i):0;let s=!1;for(let a=0;a<t.length;a++){const l=t[a];if(typeof l=="number"){if(!s&&!yt(r)&&!yt(l))return!1;if(s&&yt(l))continue;s=!1,r=l|r&1;continue}if(!s)if(r&4){if(r=2|r&1,l!==""&&!jm(n,l,e)||l===""&&t.length===1){if(yt(r))return!1;s=!0}}else if(r&8){if(i===null||!Bm(n,i,l,e)){if(yt(r))return!1;s=!0}}else{const u=t[++a],c=Um(l,i,$s(n),e);if(c===-1){if(yt(r))return!1;s=!0;continue}if(u!==""){let f;if(c>o?f="":f=i[c+1].toLowerCase(),r&2&&u!==f){if(yt(r))return!1;s=!0}}}}return yt(r)||s}function yt(n){return(n&1)===0}function Um(n,t,e,r){if(t===null)return-1;let i=0;if(r||!e){let o=!1;for(;i<t.length;){const s=t[i];if(s===n)return i;if(s===3||s===6)o=!0;else if(s===1||s===2){let a=t[++i];for(;typeof a=="string";)a=t[++i];continue}else{if(s===4)break;if(s===0){i+=4;continue}}i+=o?1:2}return-1}else return Zm(t,n)}function zm(n,t,e=!1){for(let r=0;r<t.length;r++)if(Hm(n,t[r],e))return!0;return!1}function Vm(n){for(let t=0;t<n.length;t++){const e=n[t];if(sf(e))return t}return n.length}function Zm(n,t){let e=n.indexOf(4);if(e>-1)for(e++;e<n.length;){const r=n[e];if(typeof r=="number")return-1;if(r===t)return e;e++}return-1}function Cl(n,t){return n?":not("+t.trim()+")":t}function Gm(n){let t=n[0],e=1,r=2,i="",o=!1;for(;e<n.length;){let s=n[e];if(typeof s=="string")if(r&2){const a=n[++e];i+="["+s+(a.length>0?'="'+a+'"':"")+"]"}else r&8?i+="."+s:r&4&&(i+=" "+s);else i!==""&&!yt(s)&&(t+=Cl(o,i),i=""),r=s,o=o||!yt(r);e++}return i!==""&&(t+=Cl(o,i)),t}function $m(n){return n.map(Gm).join(",")}function Wm(n){const t=[],e=[];let r=1,i=2;for(;r<n.length;){let o=n[r];if(typeof o=="string")i===2?o!==""&&t.push(o,n[++r]):i===8&&e.push(o);else{if(!yt(i))break;i=o}r++}return e.length&&t.push(1,...e),t}const ae={};function Ws(n,t,e,r,i,o,s,a,l,u,c){const f=qn+r,b=f+i,p=Ym(f,b),E=typeof u=="function"?u():u;return p[Y]={type:n,blueprint:p,template:e,queries:null,viewQuery:a,declTNode:t,data:p.slice().fill(null,f),bindingStartIndex:f,expandoStartIndex:b,hostBindingOpCodes:null,firstCreatePass:!0,firstUpdatePass:!0,staticViewQueries:!1,staticContentQueries:!1,preOrderHooks:null,preOrderCheckHooks:null,contentHooks:null,contentCheckHooks:null,viewHooks:null,viewCheckHooks:null,destroyHooks:null,cleanup:null,contentQueries:null,components:null,directiveRegistry:typeof o=="function"?o():o,pipeRegistry:typeof s=="function"?s():s,firstChild:null,schemas:l,consts:E,incompleteFirstPass:!1,ssrId:c}}function Ym(n,t){const e=[];for(let r=0;r<t;r++)e.push(r<n?null:ae);return e}function Xm(n){const t=n.tView;return t===null||t.incompleteFirstPass?n.tView=Ws(1,null,n.template,n.decls,n.vars,n.directiveDefs,n.pipeDefs,n.viewQuery,n.schemas,n.consts,n.id):t}function Ys(n,t,e,r,i,o,s,a,l,u,c){const f=t.blueprint.slice();return f[Wt]=i,f[q]=r|4|128|8|64|1024,(u!==null||n&&n[q]&2048)&&(f[q]|=2048),Pc(f),f[$n]=f[Ye]=n,f[Zn]=e,f[ie]=s||n&&n[ie],f[zn]=a||n&&n[zn],f[re]=l||n&&n[re]||null,f[Et]=o,f[Zt]=_m(),f[gr]=c,f[Sc]=u,f[It]=t.type==2?n[It]:f,f}function qm(n,t,e){const r=Yt(t,n),i=Xm(e),o=n[ie].rendererFactory,s=Xs(n,Ys(n,i,null,Lf(e),r,t,null,o.createRenderer(r,e),null,null,null));return n[t.index]=s}function Lf(n){let t=16;return n.signals?t=4096:n.onPush&&(t=64),t}function Nf(n,t,e,r){if(e===0)return-1;const i=t.length;for(let o=0;o<e;o++)t.push(r),n.blueprint.push(r),n.data.push(null);return i}function Xs(n,t){return n[yr]?n[cl][_t]=t:n[yr]=t,n[cl]=t,t}function Nn(n=1){Bf(Ct(),Fn(),De()+n)}function Bf(n,t,e,r){if((t[q]&3)===3){const o=n.preOrderCheckHooks;o!==null&&Xr(t,o,e)}else{const o=n.preOrderHooks;o!==null&&qr(t,o,0,e)}ye(e)}var Ii=(function(n){return n[n.None=0]="None",n[n.SignalBased=1]="SignalBased",n[n.HasDecoratorInputTransform=2]="HasDecoratorInputTransform",n})(Ii||{});function Vo(n,t,e,r){const i=X(null);try{const[o,s,a]=n.inputs[e];let l=null;(s&Ii.SignalBased)!==0&&(l=t[o][dr]),l!==null&&l.transformFn!==void 0?r=l.transformFn(r):a!==null&&(r=a.call(t,r)),n.setInput!==null?n.setInput(t,l,r,e,o):Kc(t,l,o,r)}finally{X(i)}}var Kt=(function(n){return n[n.Important=1]="Important",n[n.DashCase=2]="DashCase",n})(Kt||{});let Qm;function qs(n,t){return Qm(n,t)}typeof document<"u"&&document?.documentElement?.getAnimations;const Zo=new WeakMap,Go=new WeakSet;function Jm(n,t){const e=Zo.get(n);if(!e||e.length===0)return;const r=t.parentNode,i=t.previousSibling;for(let o=e.length-1;o>=0;o--){const s=e[o],a=s.parentNode;s===t?(e.splice(o,1),Go.add(s),s.dispatchEvent(new CustomEvent("animationend",{detail:{cancel:!0}}))):(i&&s===i||a&&r&&a!==r)&&(e.splice(o,1),s.dispatchEvent(new CustomEvent("animationend",{detail:{cancel:!0}})),s.parentNode?.removeChild(s))}}function Km(n,t){const e=Zo.get(n);e?e.includes(t)||e.push(t):Zo.set(n,[t])}const ze=new Set;var jf=(function(n){return n[n.CHANGE_DETECTION=0]="CHANGE_DETECTION",n[n.AFTER_NEXT_RENDER=1]="AFTER_NEXT_RENDER",n})(jf||{});const Ai=new on(""),kl=new Set;function xr(n){kl.has(n)||(kl.add(n),performance?.mark?.("mark_feature_usage",{detail:{feature:n}}))}let ng=(()=>{class n{impl=null;execute(){this.impl?.execute()}static ɵprov=fn({token:n,providedIn:"root",factory:()=>new n})}return n})();const Hf=new on("",{factory:()=>{const n=z(Vt),t=new Set;return n.onDestroy(()=>t.clear()),{queue:t,isScheduled:!1,scheduler:null,injector:n}}});function Uf(n,t,e){const r=n.get(Hf);if(Array.isArray(t))for(const i of t)r.queue.add(i),e?.detachedLeaveAnimationFns?.push(i);else r.queue.add(t),e?.detachedLeaveAnimationFns?.push(t);r.scheduler&&r.scheduler(n)}function tg(n,t){const e=n.get(Hf);if(t.detachedLeaveAnimationFns){for(const r of t.detachedLeaveAnimationFns)e.queue.delete(r);t.detachedLeaveAnimationFns=void 0}}function eg(n,t){for(const[e,r]of t)Uf(n,r.animateFns)}function xl(n,t,e,r){const i=n?.[Gt]?.enter;t!==null&&i&&i.has(e.index)&&eg(r,i)}function Tl(n,t,e,r){try{e.get(ks)}catch{return r(!1)}const i=n?.[Gt],o=rg(n,t,i);if(o.size===0){let s=!1;if(n){const a=[];Si(n,t,a),s=a.length>0}if(!s)return r(!1)}n&&ze.add(n[Zt]),Uf(e,()=>ig(n,t,i||void 0,o,r),i||void 0)}function rg(n,t,e){const r=new Map,i=e?.leave;if(i&&i.has(t.index)&&r.set(t.index,i.get(t.index)),n&&i)for(const[o,s]of i){if(r.has(o))continue;let l=n[Y].data[o].parent;for(;l;){if(l===t){r.set(o,s);break}l=l.parent}}return r}function ig(n,t,e,r,i){const o=[];if(e&&e.leave)for(const[s]of r){if(!e.leave.has(s))continue;const a=e.leave.get(s);for(const l of a.animateFns){const{promise:u}=l();o.push(u)}e.detachedLeaveAnimationFns=void 0}if(n&&Si(n,t,o),o.length>0){const s=e||n?.[Gt];if(s){const a=s.running;a&&o.push(a),s.running=Promise.allSettled(o),og(n,s.running,i)}else Promise.allSettled(o).then(()=>{n&&ze.delete(n[Zt]),i(!0)})}else n&&ze.delete(n[Zt]),i(!1)}function Si(n,t,e){if(be(t)){const i=Dt(t.index,n);Il(i,e)}else if(t.type&12){const i=n[t.index];if(Ft(i))for(let o=jn;o<i.length;o++){const s=i[o];Il(s,e)}}let r=t.child;for(;r;)Si(n,r,e),r=r.next}function Il(n,t){const e=n[Gt];if(e&&e.leave)for(const i of e.leave.values())for(const o of i.animateFns){const{promise:s}=o();t.push(s)}let r=n[Y].firstChild;for(;r;)Si(n,r,t),r=r.next}function og(n,t,e){t.then(()=>{n[Gt]?.running===t&&(n[Gt].running=void 0,ze.delete(n[Zt])),e(!0)})}function Se(n,t,e,r,i,o,s,a){if(i!=null){let l,u=!1;Ft(i)?l=i:te(i)&&(u=!0,i=i[Wt]);const c=At(i);n===0&&r!==null?(xl(a,r,o,e),s==null?Rf(t,r,c):di(t,r,c,s||null,!0)):n===1&&r!==null?(xl(a,r,o,e),di(t,r,c,s||null,!0),Jm(o,c)):n===2?(a?.[Gt]?.leave?.has(o.index)&&Km(o,c),Tl(a,o,e,f=>{if(Go.has(c)){Go.delete(c);return}Of(t,c,u,f)})):n===3&&Tl(a,o,e,()=>{t.destroyNode(c)}),l!=null&&yg(t,n,e,l,o,r,s)}}function sg(n,t){zf(n,t),t[Wt]=null,t[Et]=null}function ag(n,t,e,r,i,o){r[Wt]=i,r[Et]=t,Ri(n,r,e,1,i,o)}function zf(n,t){t[ie].changeDetectionScheduler?.notify(9),Ri(n,t,t[zn],2,null,null)}function lg(n){let t=n[yr];if(!t)return Ji(n[Y],n);for(;t;){let e=null;if(te(t))e=t[yr];else{const r=t[jn];r&&(e=r)}if(!e){for(;t&&!t[_t]&&t!==n;)te(t)&&Ji(t[Y],t),t=t[$n];t===null&&(t=n),te(t)&&Ji(t[Y],t),e=t&&t[_t]}t=e}}function Qs(n,t){const e=n[ii],r=e.indexOf(t);e.splice(r,1)}function Fi(n,t){if(Xe(t))return;const e=t[zn];e.destroyNode&&Ri(n,t,e,3,null,null),lg(t)}function Ji(n,t){if(Xe(t))return;const e=X(null);try{t[q]&=-129,t[q]|=256,t[ut]&&Uu(t[ut]),cg(n,t),ug(n,t),t[Y].type===1&&t[zn].destroy();const r=t[je];if(r!==null&&Ft(t[$n])){r!==t[$n]&&Qs(r,t);const i=t[He];i!==null&&i.detachView(n)}Uo(t)}finally{X(e)}}function ug(n,t){const e=n.cleanup,r=t[ti];if(e!==null)for(let s=0;s<e.length-1;s+=2)if(typeof e[s]=="string"){const a=e[s+3];a>=0?r[a]():r[-a].unsubscribe(),s+=2}else{const a=r[e[s+1]];e[s].call(a)}r!==null&&(t[ti]=null);const i=t[ne];if(i!==null){t[ne]=null;for(let s=0;s<i.length;s++){const a=i[s];a()}}const o=t[ei];if(o!==null){t[ei]=null;for(const s of o)s.destroy()}}function cg(n,t){let e;if(n!=null&&(e=n.destroyHooks)!=null)for(let r=0;r<e.length;r+=2){const i=t[e[r]];if(!(i instanceof kr)){const o=e[r+1];if(Array.isArray(o))for(let s=0;s<o.length;s+=2){const a=i[o[s]],l=o[s+1];En(mn.LifecycleHookStart,a,l);try{l.call(a)}finally{En(mn.LifecycleHookEnd,a,l)}}else{En(mn.LifecycleHookStart,i,o);try{o.call(i)}finally{En(mn.LifecycleHookEnd,i,o)}}}}}function fg(n,t,e){return dg(n,t.parent,e)}function dg(n,t,e){let r=t;for(;r!==null&&r.type&168;)t=r,r=t.parent;if(r===null)return e[Wt];if(be(r)){const{encapsulation:i}=n.data[r.directiveStart+r.componentOffset];if(i===Ht.None||i===Ht.Emulated)return null}return Yt(r,e)}function hg(n,t,e){return mg(n,t,e)}function pg(n,t,e){return n.type&40?Yt(n,e):null}let mg=pg;function Js(n,t,e,r){const i=fg(n,r,t),o=t[zn],s=r.parent||t[Et],a=hg(s,r,t);if(i!=null)if(Array.isArray(e))for(let l=0;l<e.length;l++)El(o,i,e[l],a,!1);else El(o,i,e,a,!1)}function or(n,t){if(t!==null){const e=t.type;if(e&3)return Yt(t,n);if(e&4)return $o(-1,n[t.index]);if(e&8){const r=t.child;if(r!==null)return or(n,r);{const i=n[t.index];return Ft(i)?$o(-1,i):At(i)}}else{if(e&128)return or(n,t.next);if(e&32)return qs(t,n)()||At(n[t.index]);{const r=Vf(n,t);if(r!==null){if(Array.isArray(r))return r[0];const i=ge(n[It]);return or(i,r)}else return or(n,t.next)}}}return null}function Vf(n,t){if(t!==null){const r=n[It][Et],i=t.projection;return r.projection[i]}return null}function $o(n,t){const e=jn+n+1;if(e<t.length){const r=t[e],i=r[Y].firstChild;if(i!==null)return or(r,i)}return t[me]}function Ks(n,t,e,r,i,o,s){for(;e!=null;){const a=r[re];if(e.type===128){e=e.next;continue}const l=r[e.index],u=e.type;if(s&&t===0&&(l&&Ue(At(l),r),e.flags|=2),!Tf(e))if(u&8)Ks(n,t,e.child,r,i,o,!1),Se(t,n,a,i,l,e,o,r);else if(u&32){const c=qs(e,r);let f;for(;f=c();)Se(t,n,a,i,f,e,o,r);Se(t,n,a,i,l,e,o,r)}else u&16?gg(n,t,r,e,i,o):Se(t,n,a,i,l,e,o,r);e=s?e.projectionNext:e.next}}function Ri(n,t,e,r,i,o){Ks(e,r,n.firstChild,t,i,o,!1)}function gg(n,t,e,r,i,o){const s=e[It],l=s[Et].projection[r.projection];if(Array.isArray(l))for(let u=0;u<l.length;u++){const c=l[u];Se(t,n,e[re],i,c,r,o,e)}else{let u=l;const c=s[$n];gf(r)&&(u.flags|=128),Ks(n,t,u,c,i,o,!0)}}function yg(n,t,e,r,i,o,s){const a=r[me],l=At(r);a!==l&&Se(t,n,e,o,a,i,s);for(let u=jn;u<r.length;u++){const c=r[u];Ri(c[Y],c,n,t,o,a)}}function vg(n,t,e,r,i){if(t)i?n.addClass(e,r):n.removeClass(e,r);else{let o=r.indexOf("-")===-1?void 0:Kt.DashCase;i==null?n.removeStyle(e,r,o):(typeof i=="string"&&i.endsWith("!important")&&(i=i.slice(0,-10),o|=Kt.Important),n.setStyle(e,r,i,o))}}function Zf(n,t,e,r,i){const o=De(),s=r&2;try{ye(-1),s&&t.length>qn&&Bf(n,t,qn,!1);const a=s?mn.TemplateUpdateStart:mn.TemplateCreateStart;En(a,i,e),e(r,i)}finally{ye(o);const a=s?mn.TemplateUpdateEnd:mn.TemplateCreateEnd;En(a,i,e)}}function Gf(n,t,e){Cg(n,t,e),(e.flags&64)===64&&kg(n,t,e)}function na(n,t,e=Yt){const r=t.localNames;if(r!==null){let i=t.index+1;for(let o=0;o<r.length;o+=2){const s=r[o+1],a=s===-1?e(t,n):n[s];n[i++]=a}}}function wg(n,t,e,r){const o=r.get(Im,xf)||e===Ht.ShadowDom||e===Ht.ExperimentalIsolatedShadowDom;return n.selectRootElement(t,o)}function bg(n){return n==="class"?"className":n==="for"?"htmlFor":n==="formaction"?"formAction":n==="innerHtml"?"innerHTML":n==="readonly"?"readOnly":n==="tabindex"?"tabIndex":n}function _g(n,t,e,r,i,o){const s=t[Y];if(ta(n,s,t,e,r)){be(n)&&Eg(t,n.index);return}n.type&3&&(e=bg(e)),Dg(n,t,e,r,i,o)}function Dg(n,t,e,r,i,o){if(n.type&3){const s=Yt(n,t);r=o!=null?o(r,n.value||"",e):r,i.setProperty(s,e,r)}else n.type&12}function Eg(n,t){const e=Dt(t,n);e[q]&16||(e[q]|=64)}function Cg(n,t,e){const r=e.directiveStart,i=e.directiveEnd;be(e)&&qm(t,e,n.data[r+e.componentOffset]),n.firstCreatePass||fi(e,t);const o=e.initialInputs;for(let s=r;s<i;s++){const a=n.data[s],l=Ho(t,n,s,e);if(Ue(l,t),o!==null&&Ig(t,s-r,l,a,e,o),_e(a)){const u=Dt(e.index,t);u[Zn]=Ho(t,n,s,e)}}}function kg(n,t,e){const r=e.directiveStart,i=e.directiveEnd,o=e.index,s=k0();try{ye(o);for(let a=r;a<i;a++){const l=n.data[a],u=t[a];Lo(a),(l.hostBindings!==null||l.hostVars!==0||l.hostAttrs!==null)&&xg(l,u)}}finally{ye(-1),Lo(s)}}function xg(n,t){n.hostBindings!==null&&n.hostBindings(1,t)}function Tg(n,t){const e=n.directiveRegistry;let r=null;if(e)for(let i=0;i<e.length;i++){const o=e[i];zm(t,o.selectors,!1)&&(r??=[],_e(o)?r.unshift(o):r.push(o))}return r}function Ig(n,t,e,r,i,o){const s=o[t];if(s!==null)for(let a=0;a<s.length;a+=2){const l=s[a],u=s[a+1];Vo(r,e,l,u)}}function $f(n,t,e,r,i){const o=qn+e,s=t[Y],a=i(s,t,n,r,e);t[o]=a,Er(n,!0);const l=n.type===2;return l?(Pf(t[zn],a,n),(m0()===0||Fs(n))&&Ue(a,t),g0()):Ue(a,t),Ls()&&(!l||!Tf(n))&&Js(s,t,a,n),n}function Wf(n){let t=n;return Uc()?b0():(t=t.parent,Er(t,!1)),t}function Ag(n,t){const e=n[re];if(!e)return;let r;try{r=e.get(Qe,null)}catch{r=null}r?.(t)}function ta(n,t,e,r,i){const o=n.inputs?.[r],s=n.hostDirectiveInputs?.[r];let a=!1;if(s)for(let l=0;l<s.length;l+=2){const u=s[l],c=s[l+1],f=t.data[u];Vo(f,e[u],c,i),a=!0}if(o)for(const l of o){const u=e[l],c=t.data[l];Vo(c,u,r,i),a=!0}return a}function Sg(n,t){const e=Dt(t,n),r=e[Y];Fg(r,e);const i=e[Wt];i!==null&&e[gr]===null&&(e[gr]=If(i,e[re])),En(mn.ComponentStart);try{ea(r,e,e[Zn])}finally{En(mn.ComponentEnd,e[Zn])}}function Fg(n,t){for(let e=t.length;e<n.blueprint.length;e++)t.push(n.blueprint[e])}function ea(n,t,e){Ps(t);try{const r=n.viewQuery;r!==null&&zo(1,r,e);const i=n.template;i!==null&&Zf(n,t,i,1,e),n.firstCreatePass&&(n.firstCreatePass=!1),t[He]?.finishViewCreation(n),n.staticContentQueries&&Af(n,t),n.staticViewQueries&&zo(2,n.viewQuery,e);const o=n.components;o!==null&&Rg(t,o)}catch(r){throw n.firstCreatePass&&(n.incompleteFirstPass=!0,n.firstCreatePass=!1),r}finally{t[q]&=-5,Ms()}}function Rg(n,t){for(let e=0;e<t.length;e++)Sg(n,t[e])}function Oi(n,t,e,r){const i=X(null);try{const o=t.tView,a=n[q]&4096?4096:16,l=Ys(n,o,e,a,null,t,null,null,r?.injector??null,r?.embeddedViewInjector??null,r?.dehydratedView??null),u=n[t.index];l[je]=u;const c=n[He];return c!==null&&(l[He]=c.createEmbeddedView(o)),ea(o,l,e),l}finally{X(i)}}function vr(n,t){return!t||t.firstChild===null||gf(n)}function hi(n,t,e,r,i=!1){for(;e!==null;){if(e.type===128){e=i?e.projectionNext:e.next;continue}const o=t[e.index];o!==null&&r.push(At(o)),Ft(o)&&Og(o,r);const s=e.type;if(s&8)hi(n,t,e.child,r);else if(s&32){const a=qs(e,t);let l;for(;l=a();)r.push(l)}else if(s&16){const a=Vf(t,e);if(Array.isArray(a))r.push(...a);else{const l=ge(t[It]);hi(l[Y],l,a,r,!0)}}e=i?e.projectionNext:e.next}return r}function Og(n,t){for(let e=jn;e<n.length;e++){const r=n[e],i=r[Y].firstChild;i!==null&&hi(r[Y],r,i,t)}n[me]!==n[Wt]&&t.push(n[me])}function Yf(n){if(n[Xi]!==null){for(const t of n[Xi])t.impl.addSequence(t);n[Xi].length=0}}let Xf=[];function Pg(n){return n[ut]??Mg(n)}function Mg(n){const t=Xf.pop()??Object.create(Ng);return t.lView=n,t}function Lg(n){n.lView[ut]!==n&&(n.lView=null,Xf.push(n))}const Ng={...ms,consumerIsAlwaysLive:!0,kind:"template",consumerMarkedDirty:n=>{Ei(n.lView)},consumerOnSignalRead(){this.lView[ut]=this}};function Bg(n){const t=n[ut]??Object.create(jg);return t.lView=n,t}const jg={...ms,consumerIsAlwaysLive:!0,kind:"template",consumerMarkedDirty:n=>{let t=ge(n.lView);for(;t&&!qf(t[Y]);)t=ge(t);t&&Mc(t)},consumerOnSignalRead(){this.lView[ut]=this}};function qf(n){return n.type!==2}function Qf(n){if(n[ei]===null)return;let t=!0;for(;t;){let e=!1;for(const r of n[ei])r.dirty&&(e=!0,r.zone===null||Zone.current===r.zone?r.run():r.zone.run(()=>r.run()));t=e&&!!(n[q]&8192)}}const Hg=100;function Jf(n,t=0){const r=n[ie].rendererFactory;r.begin?.();try{Ug(n,t)}finally{r.end?.()}}function Ug(n,t){const e=zc();try{dl(!0),Wo(n,t);let r=0;for(;si(n);){if(r===Hg)throw new V(103,!1);r++,Wo(n,1)}}finally{dl(e)}}function zg(n,t,e,r){if(Xe(t))return;const i=t[q],o=!1,s=!1;Ps(t);let a=!0,l=null,u=null;qf(n)?(u=Pg(t),l=Ka(u)):bh()===null?(a=!1,u=Bg(t),l=Ka(u)):t[ut]&&(Uu(t[ut]),t[ut]=null);try{Pc(t),_0(n.bindingStartIndex),e!==null&&Zf(n,t,e,2,r);const c=(i&3)===3;if(!o)if(c){const p=n.preOrderCheckHooks;p!==null&&Xr(t,p,null)}else{const p=n.preOrderHooks;p!==null&&qr(t,p,0,null),qi(t,0)}if(s||Vg(t),Qf(t),Kf(t,0),n.contentQueries!==null&&Af(n,t),!o)if(c){const p=n.contentCheckHooks;p!==null&&Xr(t,p)}else{const p=n.contentHooks;p!==null&&qr(t,p,1),qi(t,1)}Gg(n,t);const f=n.components;f!==null&&td(t,f,0);const b=n.viewQuery;if(b!==null&&zo(2,b,r),!o)if(c){const p=n.viewCheckHooks;p!==null&&Xr(t,p)}else{const p=n.viewHooks;p!==null&&qr(t,p,2),qi(t,2)}if(n.firstUpdatePass===!0&&(n.firstUpdatePass=!1),t[Yi]){for(const p of t[Yi])p();t[Yi]=null}o||(Yf(t),t[q]&=-73)}catch(c){throw Ei(t),c}finally{u!==null&&(xh(u,l),a&&Lg(u)),Ms()}}function Kf(n,t){for(let e=wf(n);e!==null;e=bf(e))for(let r=jn;r<e.length;r++){const i=e[r];nd(i,t)}}function Vg(n){for(let t=wf(n);t!==null;t=bf(t)){if(!(t[q]&2))continue;const e=t[ii];for(let r=0;r<e.length;r++){const i=e[r];Mc(i)}}}function Zg(n,t,e){En(mn.ComponentStart);const r=Dt(t,n);try{nd(r,e)}finally{En(mn.ComponentEnd,r[Zn])}}function nd(n,t){Os(n)&&Wo(n,t)}function Wo(n,t){const r=n[Y],i=n[q],o=n[ut];let s=!!(t===0&&i&16);if(s||=!!(i&64&&t===0),s||=!!(i&1024),s||=!!(o?.dirty&&Hu(o)),s||=!1,o&&(o.dirty=!1),n[q]&=-9217,s)zg(r,n,r.template,n[Zn]);else if(i&8192){const a=X(null);try{Qf(n),Kf(n,1);const l=r.components;l!==null&&td(n,l,1),Yf(n)}finally{X(a)}}}function td(n,t,e){for(let r=0;r<t.length;r++)Zg(n,t[r],e)}function Gg(n,t){const e=n.hostBindingOpCodes;if(e!==null)try{for(let r=0;r<e.length;r++){const i=e[r];if(i<0)ye(~i);else{const o=i,s=e[++r],a=e[++r];C0(s,o);const l=t[o];En(mn.HostBindingsUpdateStart,l);try{a(2,l)}finally{En(mn.HostBindingsUpdateEnd,l)}}}}finally{ye(-1)}}function ra(n,t){const e=zc()?64:1088;for(n[ie].changeDetectionScheduler?.notify(t);n;){n[q]|=e;const r=ge(n);if(oi(n)&&!r)return n;n=r}return null}function ed(n,t,e,r){return[n,!0,0,t,null,r,null,e,null,null]}function rd(n,t){const e=jn+t;if(e<n.length)return n[e]}function Pi(n,t,e,r=!0){const i=t[Y];if($g(i,t,n,e),r){const s=$o(e,n),a=t[zn],l=a.parentNode(n[me]);l!==null&&ag(i,n[Et],a,t,l,s)}const o=t[gr];o!==null&&o.firstChild!==null&&(o.firstChild=null)}function id(n,t){const e=wr(n,t);return e!==void 0&&Fi(e[Y],e),e}function wr(n,t){if(n.length<=jn)return;const e=jn+t,r=n[e];if(r){const i=r[je];i!==null&&i!==n&&Qs(i,r),t>0&&(n[e-1][_t]=r[_t]);const o=ni(n,jn+t);sg(r[Y],r);const s=o[He];s!==null&&s.detachView(o[Y]),r[$n]=null,r[_t]=null,r[q]&=-129}return r}function $g(n,t,e,r){const i=jn+r,o=e.length;r>0&&(e[i-1][_t]=t),r<o-jn?(t[_t]=e[i],Ec(e,jn+r,t)):(e.push(t),t[_t]=null),t[$n]=e;const s=t[je];s!==null&&e!==s&&od(s,t);const a=t[He];a!==null&&a.insertView(n),Po(t),t[q]|=128}function od(n,t){const e=n[ii],r=t[$n];if(te(r))n[q]|=2;else{const i=r[$n][It];t[It]!==i&&(n[q]|=2)}e===null?n[ii]=[t]:e.push(t)}class br{_lView;_cdRefInjectingView;_appRef=null;_attachedToViewContainer=!1;exhaustive;get rootNodes(){const t=this._lView,e=t[Y];return hi(e,t,e.firstChild,[])}constructor(t,e){this._lView=t,this._cdRefInjectingView=e}get context(){return this._lView[Zn]}set context(t){this._lView[Zn]=t}get destroyed(){return Xe(this._lView)}destroy(){if(this._appRef)this._appRef.detachView(this);else if(this._attachedToViewContainer){const t=this._lView[$n];if(Ft(t)){const e=t[ri],r=e?e.indexOf(this):-1;r>-1&&(wr(t,r),ni(e,r))}this._attachedToViewContainer=!1}Fi(this._lView[Y],this._lView)}onDestroy(t){Lc(this._lView,t)}markForCheck(){ra(this._cdRefInjectingView||this._lView,4)}detach(){this._lView[q]&=-129}reattach(){Po(this._lView),this._lView[q]|=128}detectChanges(){this._lView[q]|=1024,Jf(this._lView)}checkNoChanges(){}attachToViewContainerRef(){if(this._appRef)throw new V(902,!1);this._attachedToViewContainer=!0}detachFromAppRef(){this._appRef=null;const t=oi(this._lView),e=this._lView[je];e!==null&&!t&&Qs(e,this._lView),zf(this._lView[Y],this._lView)}attachToAppRef(t){if(this._attachedToViewContainer)throw new V(902,!1);this._appRef=t;const e=oi(this._lView),r=this._lView[je];r!==null&&!e&&od(r,this._lView),Po(this._lView)}}let Je=(()=>{class n{_declarationLView;_declarationTContainer;elementRef;static __NG_ELEMENT_ID__=Wg;constructor(e,r,i){this._declarationLView=e,this._declarationTContainer=r,this.elementRef=i}get ssrId(){return this._declarationTContainer.tView?.ssrId||null}createEmbeddedView(e,r){return this.createEmbeddedViewImpl(e,r)}createEmbeddedViewImpl(e,r,i){const o=Oi(this._declarationLView,this._declarationTContainer,e,{embeddedViewInjector:r,dehydratedView:i});return new br(o)}}return n})();function Wg(){return Yg(ft(),Fn())}function Yg(n,t){return n.type&4?new Je(t,n,Ti(n,t)):null}function Mi(n,t,e,r,i){let o=n.data[t];if(o===null)o=Xg(n,t,e,r,i),E0()&&(o.flags|=32);else if(o.type&64){o.type=e,o.value=r,o.attrs=i;const s=w0();o.injectorIndex=s===null?-1:s.injectorIndex}return Er(o,!0),o}function Xg(n,t,e,r,i){const o=Hc(),s=Uc(),a=s?o:o&&o.parent,l=n.data[t]=Qg(n,a,e,t,r,i);return qg(n,l,o,s),l}function qg(n,t,e,r){n.firstChild===null&&(n.firstChild=t),e!==null&&(r?e.child==null&&t.parent!==null&&(e.child=t):e.next===null&&(e.next=t,t.prev=e))}function Qg(n,t,e,r,i,o){let s=t?t.injectorIndex:-1,a=0;return v0()&&(a|=128),{type:e,index:r,insertBeforeIndex:null,injectorIndex:s,directiveStart:-1,directiveEnd:-1,directiveStylingLast:-1,componentOffset:-1,controlDirectiveIndex:-1,customControlIndex:-1,propertyBindings:null,flags:a,providerIndexes:0,value:i,attrs:o,mergedAttrs:null,localNames:null,initialInputs:null,inputs:null,hostDirectiveInputs:null,outputs:null,hostDirectiveOutputs:null,directiveToIndex:null,tView:null,next:null,prev:null,projectionNext:null,child:null,parent:t,projection:null,styles:null,stylesWithoutHost:null,residualStyles:void 0,classes:null,classesWithoutHost:null,residualClasses:void 0,classBindings:0,styleBindings:0}}function Jg(n){const t=n[fl]??[],r=n[$n][zn],i=[];for(const o of t)o.data[Tm]!==void 0?i.push(o):Kg(o,r);n[fl]=i}function Kg(n,t){let e=0,r=n.firstChild;if(r){const i=n.data[xm];for(;e<i;){const o=r.nextSibling;Of(t,r,!1),r=o,e++}}}let ny=()=>null,ty=()=>null;function Yo(n,t){return ny()}function sd(n,t,e){return ty()}let ey=class{},ad=class{};class ry{resolveComponentFactory(t){throw new V(917,!1)}}let Li=class{static NULL=new ry};class ia{}let ld=(()=>{class n{destroyNode=null;static __NG_ELEMENT_ID__=()=>iy()}return n})();function iy(){const n=Fn(),t=ft(),e=Dt(t.index,n);return(te(e)?e:n)[zn]}let oy=(()=>{class n{static ɵprov=fn({token:n,providedIn:"root",factory:()=>null})}return n})();const Ki={};class sy{injector;parentInjector;constructor(t,e){this.injector=t,this.parentInjector=e}get(t,e,r){const i=this.injector.get(t,Ki,r);return i!==Ki||e===Ki?i:this.parentInjector.get(t,e,r)}}function pi(n,t,e){let r=e?n.styles:null,i=e?n.classes:null,o=0;if(t!==null)for(let s=0;s<t.length;s++){const a=t[s];if(typeof a=="number")o=a;else if(o==1)i=il(i,a);else if(o==2){const l=a,u=t[++s];r=il(r,l+": "+u+";")}}e?n.styles=r:n.stylesWithoutHost=r,e?n.classes=i:n.classesWithoutHost=i}function an(n,t=0){const e=Fn();if(e===null)return cn(n,t);const r=ft();return hf(r,e,Jn(n),t)}function ay(n,t,e,r,i){const o=r===null?null:{"":-1},s=i(n,e);if(s!==null){let a=s,l=null,u=null;for(const c of s)if(c.resolveHostDirectives!==null){[a,l,u]=c.resolveHostDirectives(s);break}cy(n,t,e,a,o,l,u)}o!==null&&r!==null&&ly(e,r,o)}function ly(n,t,e){const r=n.localNames=[];for(let i=0;i<t.length;i+=2){const o=e[t[i+1]];if(o==null)throw new V(-301,!1);r.push(t[i],o)}}function uy(n,t,e){t.componentOffset=e,(n.components??=[]).push(t.index)}function cy(n,t,e,r,i,o,s){const a=r.length;let l=null;for(let b=0;b<a;b++){const p=r[b];l===null&&_e(p)&&(l=p,uy(n,e,b)),jo(fi(e,t),n,p.type)}gy(e,n.data.length,a),l?.viewProvidersResolver&&l.viewProvidersResolver(l);for(let b=0;b<a;b++){const p=r[b];p.providersResolver&&p.providersResolver(p)}let u=!1,c=!1,f=Nf(n,t,a,null);a>0&&(e.directiveToIndex=new Map);for(let b=0;b<a;b++){const p=r[b];if(e.mergedAttrs=xi(e.mergedAttrs,p.hostAttrs),dy(n,e,t,f,p),my(f,p,i),s!==null&&s.has(p)){const[A,F]=s.get(p);e.directiveToIndex.set(p.type,[f,A+e.directiveStart,F+e.directiveStart])}else(o===null||!o.has(p))&&e.directiveToIndex.set(p.type,f);p.contentQueries!==null&&(e.flags|=4),(p.hostBindings!==null||p.hostAttrs!==null||p.hostVars!==0)&&(e.flags|=64);const E=p.type.prototype;!u&&(E.ngOnChanges||E.ngOnInit||E.ngDoCheck)&&((n.preOrderHooks??=[]).push(e.index),u=!0),!c&&(E.ngOnChanges||E.ngDoCheck)&&((n.preOrderCheckHooks??=[]).push(e.index),c=!0),f++}fy(n,e,o)}function fy(n,t,e){for(let r=t.directiveStart;r<t.directiveEnd;r++){const i=n.data[r];if(e===null||!e.has(i))Al(0,t,i,r),Al(1,t,i,r),Fl(t,r,!1);else{const o=e.get(i);Sl(0,t,o,r),Sl(1,t,o,r),Fl(t,r,!0)}}}function Al(n,t,e,r){const i=n===0?e.inputs:e.outputs;for(const o in i)if(i.hasOwnProperty(o)){let s;n===0?s=t.inputs??={}:s=t.outputs??={},s[o]??=[],s[o].push(r),ud(t,o)}}function Sl(n,t,e,r){const i=n===0?e.inputs:e.outputs;for(const o in i)if(i.hasOwnProperty(o)){const s=i[o];let a;n===0?a=t.hostDirectiveInputs??={}:a=t.hostDirectiveOutputs??={},a[s]??=[],a[s].push(r,o),ud(t,s)}}function ud(n,t){t==="class"?n.flags|=8:t==="style"&&(n.flags|=16)}function Fl(n,t,e){const{attrs:r,inputs:i,hostDirectiveInputs:o}=n;if(r===null||!e&&i===null||e&&o===null||$s(n)){n.initialInputs??=[],n.initialInputs.push(null);return}let s=null,a=0;for(;a<r.length;){const l=r[a];if(l===0){a+=4;continue}else if(l===5){a+=2;continue}else if(typeof l=="number")break;if(!e&&i.hasOwnProperty(l)){const u=i[l];for(const c of u)if(c===t){s??=[],s.push(l,r[a+1]);break}}else if(e&&o.hasOwnProperty(l)){const u=o[l];for(let c=0;c<u.length;c+=2)if(u[c]===t){s??=[],s.push(u[c+1],r[a+1]);break}}a+=2}n.initialInputs??=[],n.initialInputs.push(s)}function dy(n,t,e,r,i){n.data[r]=i;const o=i.factory||(i.factory=mr(i.type,!0)),s=new kr(o,_e(i),an,null);n.blueprint[r]=s,e[r]=s,hy(n,t,r,Nf(n,e,i.hostVars,ae),i)}function hy(n,t,e,r,i){const o=i.hostBindings;if(o){let s=n.hostBindingOpCodes;s===null&&(s=n.hostBindingOpCodes=[]);const a=~t.index;py(s)!=a&&s.push(a),s.push(e,r,o)}}function py(n){let t=n.length;for(;t>0;){const e=n[--t];if(typeof e=="number"&&e<0)return e}return 0}function my(n,t,e){if(e){if(t.exportAs)for(let r=0;r<t.exportAs.length;r++)e[t.exportAs[r]]=n;_e(t)&&(e[""]=n)}}function gy(n,t,e){n.flags|=1,n.directiveStart=t,n.directiveEnd=t+e,n.providerIndexes=t}function cd(n,t,e,r,i,o,s,a){const l=t[Y],u=l.consts,c=oe(u,s),f=Mi(l,n,e,r,c);return ay(l,t,f,oe(u,a),i),f.mergedAttrs=xi(f.mergedAttrs,f.attrs),f.attrs!==null&&pi(f,f.attrs,!1),f.mergedAttrs!==null&&pi(f,f.mergedAttrs,!0),l.queries!==null&&l.queries.elementStart(l,f),f}function fd(n,t){em(n,t),Rc(t)&&n.queries.elementEnd(t)}function yy(n,t,e,r,i,o){const s=t.consts,a=oe(s,i),l=Mi(t,n,e,r,a);if(l.mergedAttrs=xi(l.mergedAttrs,l.attrs),o!=null){const u=oe(s,o);l.localNames=[];for(let c=0;c<u.length;c+=2)l.localNames.push(u[c],-1)}return l.attrs!==null&&pi(l,l.attrs,!1),l.mergedAttrs!==null&&pi(l,l.mergedAttrs,!0),t.queries!==null&&t.queries.elementStart(t,l),l}function dd(n){return oa(n)?Array.isArray(n)||!(n instanceof Map)&&Symbol.iterator in n:!1}function vy(n,t){if(Array.isArray(n))for(let e=0;e<n.length;e++)t(n[e]);else{const e=n[Symbol.iterator]();let r;for(;!(r=e.next()).done;)t(r.value)}}function oa(n){return n!==null&&(typeof n=="function"||typeof n=="object")}function Tr(n,t,e){if(e===ae)return!1;const r=n[t];return Object.is(r,e)?!1:(n[t]=e,!0)}function no(n,t,e){return function r(i){const o=be(n)?Dt(n.index,t):t;ra(o,5);const s=t[Zn];let a=Rl(t,s,e,i),l=r.__ngNextListenerFn__;for(;l;)a=Rl(t,s,l,i)&&a,l=l.__ngNextListenerFn__;return a}}function Rl(n,t,e,r){const i=X(null);try{return En(mn.OutputStart,t,e),e(r)!==!1}catch(o){return Ag(n,o),!1}finally{En(mn.OutputEnd,t,e),X(i)}}function wy(n,t,e,r,i,o,s,a){const l=Fs(n);let u=!1,c=null;if(!r&&l&&(c=_y(t,e,o,n.index)),c!==null){const f=c.__ngLastListenerFn__||c;f.__ngNextListenerFn__=s,c.__ngLastListenerFn__=s,u=!0}else{const f=Yt(n,e),b=r?r(f):f,p=i.listen(b,o,a);if(!by(o)){const E=r?A=>r(At(A[n.index])):n.index;hd(E,t,e,o,a,p,!1)}}return u}function by(n){return n.startsWith("animation")||n.startsWith("transition")}function _y(n,t,e,r){const i=n.cleanup;if(i!=null)for(let o=0;o<i.length-1;o+=2){const s=i[o];if(s===e&&i[o+1]===r){const a=t[ti],l=i[o+2];return a&&a.length>l?a[l]:null}typeof s=="string"&&(o+=2)}return null}function hd(n,t,e,r,i,o,s){const a=t.firstCreatePass?p0(t):null,l=h0(e),u=l.length;l.push(i,o),a&&a.push(r,n,u,(u+1)*(s?-1:1))}function Ol(n,t,e,r,i,o){const s=t[e],a=t[Y],u=a.data[e].outputs[r],f=s[u].subscribe(o);hd(n.index,a,t,i,o,f,!0)}const Xo=Symbol("BINDING");class pd extends Li{ngModule;constructor(t){super(),this.ngModule=t}resolveComponentFactory(t){const e=hr(t);return new md(e,this.ngModule)}}function Dy(n){return Object.keys(n).map(t=>{const[e,r,i]=n[t],o={propName:e,templateName:t,isSignal:(r&Ii.SignalBased)!==0};return i&&(o.transform=i),o})}function Ey(n){return Object.keys(n).map(t=>({propName:n[t],templateName:t}))}function Cy(n,t,e){let r=t instanceof Vt?t:t?.injector;return r&&n.getStandaloneInjector!==null&&(r=n.getStandaloneInjector(r)||r),r?new sy(e,r):e}function ky(n){const t=n.get(ia,null);if(t===null)throw new V(407,!1);const e=n.get(oy,null),r=n.get(Us,null);return{rendererFactory:t,sanitizer:e,changeDetectionScheduler:r,ngReflect:!1}}function xy(n,t){const e=Ty(n);return Ff(t,e,e==="svg"?l0:e==="math"?u0:null)}function Ty(n){return(n.selectors[0][0]||"div").toLowerCase()}class md extends ad{componentDef;ngModule;selector;componentType;ngContentSelectors;isBoundToModule;cachedInputs=null;cachedOutputs=null;get inputs(){return this.cachedInputs??=Dy(this.componentDef.inputs),this.cachedInputs}get outputs(){return this.cachedOutputs??=Ey(this.componentDef.outputs),this.cachedOutputs}constructor(t,e){super(),this.componentDef=t,this.ngModule=e,this.componentType=t.type,this.selector=$m(t.selectors),this.ngContentSelectors=t.ngContentSelectors??[],this.isBoundToModule=!!e}create(t,e,r,i,o,s){En(mn.DynamicComponentStart);const a=X(null);try{const l=this.componentDef,u=Iy(r,l,s,o),c=Cy(l,i||this.ngModule,t),f=ky(c),b=f.rendererFactory.createRenderer(null,l),p=r?wg(b,r,l.encapsulation,c):xy(l,b),E=s?.some(Pl)||o?.some(S=>typeof S!="function"&&S.bindings.some(Pl)),A=Ys(null,u,null,512|Lf(l),null,null,f,b,c,null,If(p,c,!0));A[qn]=p,Ps(A);let F=null;try{const S=cd(qn,A,2,"#host",()=>u.directiveRegistry,!0,0);Pf(b,p,S),Ue(p,A),Gf(u,A,S),Sf(u,S,A),fd(u,S),e!==void 0&&Fy(S,this.ngContentSelectors,e),F=Dt(S.index,A),A[Zn]=F[Zn],ea(u,A,null)}catch(S){throw F!==null&&Uo(F),Uo(A),S}finally{En(mn.DynamicComponentEnd),Ms()}return new Sy(this.componentType,A,!!E)}finally{X(a)}}}function Iy(n,t,e,r){const i=n?["ng-version","21.2.1"]:Wm(t.selectors[0]);let o=null,s=null,a=0;if(e)for(const c of e)a+=c[Xo].requiredVars,c.create&&(c.targetIdx=0,(o??=[]).push(c)),c.update&&(c.targetIdx=0,(s??=[]).push(c));if(r)for(let c=0;c<r.length;c++){const f=r[c];if(typeof f!="function")for(const b of f.bindings){a+=b[Xo].requiredVars;const p=c+1;b.create&&(b.targetIdx=p,(o??=[]).push(b)),b.update&&(b.targetIdx=p,(s??=[]).push(b))}}const l=[t];if(r)for(const c of r){const f=typeof c=="function"?c:c.type,b=yc(f);l.push(b)}return Ws(0,null,Ay(o,s),1,a,l,null,null,null,[i],null)}function Ay(n,t){return!n&&!t?null:e=>{if(e&1&&n)for(const r of n)r.create();if(e&2&&t)for(const r of t)r.update()}}function Pl(n){const t=n[Xo].kind;return t==="input"||t==="twoWay"}class Sy extends ey{_rootLView;_hasInputBindings;instance;hostView;changeDetectorRef;componentType;location;previousInputValues=null;_tNode;constructor(t,e,r){super(),this._rootLView=e,this._hasInputBindings=r,this._tNode=Rs(e[Y],qn),this.location=Ti(this._tNode,e),this.instance=Dt(this._tNode.index,e)[Zn],this.hostView=this.changeDetectorRef=new br(e,void 0),this.componentType=t}setInput(t,e){this._hasInputBindings;const r=this._tNode;if(this.previousInputValues??=new Map,this.previousInputValues.has(t)&&Object.is(this.previousInputValues.get(t),e))return;const i=this._rootLView;ta(r,i[Y],i,t,e),this.previousInputValues.set(t,e);const o=Dt(r.index,i);ra(o,1)}get injector(){return new lr(this._tNode,this._rootLView)}destroy(){this.hostView.destroy()}onDestroy(t){this.hostView.onDestroy(t)}}function Fy(n,t,e){const r=n.projection=[];for(let i=0;i<t.length;i++){const o=e[i];r.push(o!=null&&o.length?Array.from(o):null)}}let le=(()=>{class n{static __NG_ELEMENT_ID__=Ry}return n})();function Ry(){const n=ft();return Py(n,Fn())}const Oy=le,gd=class extends Oy{_lContainer;_hostTNode;_hostLView;constructor(t,e,r){super(),this._lContainer=t,this._hostTNode=e,this._hostLView=r}get element(){return Ti(this._hostTNode,this._hostLView)}get injector(){return new lr(this._hostTNode,this._hostLView)}get parentInjector(){const t=Zs(this._hostTNode,this._hostLView);if(af(t)){const e=ci(t,this._hostLView),r=ui(t),i=e[Y].data[r+8];return new lr(i,e)}else return new lr(null,this._hostLView)}clear(){for(;this.length>0;)this.remove(this.length-1)}get(t){const e=Ml(this._lContainer);return e!==null&&e[t]||null}get length(){return this._lContainer.length-jn}createEmbeddedView(t,e,r){let i,o;typeof r=="number"?i=r:r!=null&&(i=r.index,o=r.injector);const s=Yo(this._lContainer,t.ssrId),a=t.createEmbeddedViewImpl(e||{},o,s);return this.insertImpl(a,i,vr(this._hostTNode,s)),a}createComponent(t,e,r,i,o,s,a){const l=t&&!q0(t);let u;if(l)u=e;else{const F=e||{};u=F.index,r=F.injector,i=F.projectableNodes,o=F.environmentInjector||F.ngModuleRef,s=F.directives,a=F.bindings}const c=l?t:new md(hr(t)),f=r||this.parentInjector;if(!o&&c.ngModule==null){const S=(l?f:this.parentInjector).get(Vt,null);S&&(o=S)}const b=hr(c.componentType??{}),p=Yo(this._lContainer,b?.id??null),A=c.create(f,i,null,o,s,a);return this.insertImpl(A.hostView,u,vr(this._hostTNode,p)),A}insert(t,e){return this.insertImpl(t,e,!0)}insertImpl(t,e,r){const i=t._lView;if(c0(i)){const a=this.indexOf(t);if(a!==-1)this.detach(a);else{const l=i[$n],u=new gd(l,l[Et],l[$n]);u.detach(u.indexOf(t))}}const o=this._adjustIndex(e),s=this._lContainer;return Pi(s,i,o,r),t.attachToViewContainerRef(),Ec(to(s),o,t),t}move(t,e){return this.insert(t,e)}indexOf(t){const e=Ml(this._lContainer);return e!==null?e.indexOf(t):-1}remove(t){const e=this._adjustIndex(t,-1),r=wr(this._lContainer,e);r&&(ni(to(this._lContainer),e),Fi(r[Y],r))}detach(t){const e=this._adjustIndex(t,-1),r=wr(this._lContainer,e);return r&&ni(to(this._lContainer),e)!=null?new br(r):null}_adjustIndex(t,e=0){return t??this.length+e}};function Ml(n){return n[ri]}function to(n){return n[ri]||(n[ri]=[])}function Py(n,t){let e;const r=t[n.index];return Ft(r)?e=r:(e=ed(r,t,null,n),t[n.index]=e,Xs(t,e)),Ly(e,t,n,r),new gd(e,n,t)}function My(n,t){const e=n[zn],r=e.createComment(""),i=Yt(t,n),o=e.parentNode(i);return di(e,o,r,e.nextSibling(i),!1),r}let Ly=Ny;function Ny(n,t,e,r){if(n[me])return;let i;e.type&8?i=At(r):i=My(t,e),n[me]=i}let Ve=class{};function By(n,t){return new jy(n,t??null,[])}class jy extends Ve{ngModuleType;_parent;_bootstrapComponents=[];_r3Injector;instance;destroyCbs=[];componentFactoryResolver=new pd(this);constructor(t,e,r,i=!0){super(),this.ngModuleType=t,this._parent=e;const o=Lp(t);this._bootstrapComponents=Lm(o.bootstrap),this._r3Injector=qc(t,e,[{provide:Ve,useValue:this},{provide:Li,useValue:this.componentFactoryResolver},...r],bs(t),new Set(["environment"])),i&&this.resolveInjectorInitializers()}resolveInjectorInitializers(){this._r3Injector.resolveInjectorInitializers(),this.instance=this._r3Injector.get(this.ngModuleType)}get injector(){return this._r3Injector}destroy(){const t=this._r3Injector;!t.destroyed&&t.destroy(),this.destroyCbs.forEach(e=>e()),this.destroyCbs=null}onDestroy(t){this.destroyCbs.push(t)}}class yd extends Ve{injector;componentFactoryResolver=new pd(this);instance=null;constructor(t){super();const e=new As([...t.providers,{provide:Ve,useValue:this},{provide:Li,useValue:this.componentFactoryResolver}],t.parent||Is(),t.debugName,new Set(["environment"]));this.injector=e,t.runEnvironmentInitializers&&e.resolveInjectorInitializers()}destroy(){this.injector.destroy()}onDestroy(t){this.injector.onDestroy(t)}}function Hy(n,t,e=null){return new yd({providers:n,parent:t,debugName:e,runEnvironmentInitializers:!0}).injector}let Uy=(()=>{class n{_injector;cachedInjectors=new Map;constructor(e){this._injector=e}getOrCreateStandaloneInjector(e){if(!e.standalone)return null;if(!this.cachedInjectors.has(e)){const r=xc(!1,e.type),i=r.length>0?Hy([r],this._injector,""):null;this.cachedInjectors.set(e,i)}return this.cachedInjectors.get(e)}ngOnDestroy(){try{for(const e of this.cachedInjectors.values())e!==null&&e.destroy()}finally{this.cachedInjectors.clear()}}static ɵprov=fn({token:n,providedIn:"environment",factory:()=>new n(cn(Vt))})}return n})();function vd(n){return Vs(()=>{const t=wd(n),e={...t,decls:n.decls,vars:n.vars,template:n.template,consts:n.consts||null,ngContentSelectors:n.ngContentSelectors,onPush:n.changeDetection===yf.OnPush,directiveDefs:null,pipeDefs:null,dependencies:t.standalone&&n.dependencies||null,getStandaloneInjector:t.standalone?i=>i.get(Uy).getOrCreateStandaloneInjector(e):null,getExternalStyles:null,signals:n.signals??!1,data:n.data||{},encapsulation:n.encapsulation||Ht.Emulated,styles:n.styles||wt,_:null,schemas:n.schemas||null,tView:null,id:""};t.standalone&&xr("NgStandalone"),bd(e);const r=n.dependencies;return e.directiveDefs=Ll(r,zy),e.pipeDefs=Ll(r,Np),e.id=$y(e),e})}function zy(n){return hr(n)||yc(n)}function Vy(n){return Vs(()=>({type:n.type,bootstrap:n.bootstrap||wt,declarations:n.declarations||wt,imports:n.imports||wt,exports:n.exports||wt,transitiveCompileScopes:null,schemas:n.schemas||null,id:n.id||null}))}function Zy(n,t){if(n==null)return Ne;const e={};for(const r in n)if(n.hasOwnProperty(r)){const i=n[r];let o,s,a,l;Array.isArray(i)?(a=i[0],o=i[1],s=i[2]??o,l=i[3]||null):(o=i,s=i,a=Ii.None,l=null),e[o]=[r,a,l],t[o]=s}return e}function Gy(n){if(n==null)return Ne;const t={};for(const e in n)n.hasOwnProperty(e)&&(t[n[e]]=e);return t}function kt(n){return Vs(()=>{const t=wd(n);return bd(t),t})}function it(n){return{type:n.type,name:n.name,factory:null,pure:n.pure!==!1,standalone:n.standalone??!0,onDestroy:n.type.prototype.ngOnDestroy||null}}function wd(n){const t={};return{type:n.type,providersResolver:null,viewProvidersResolver:null,factory:null,hostBindings:n.hostBindings||null,hostVars:n.hostVars||0,hostAttrs:n.hostAttrs||null,contentQueries:n.contentQueries||null,declaredInputs:t,inputConfig:n.inputs||Ne,exportAs:n.exportAs||null,standalone:n.standalone??!0,signals:n.signals===!0,selectors:n.selectors||wt,viewQuery:n.viewQuery||null,features:n.features||null,setInput:null,resolveHostDirectives:null,hostDirectives:null,controlDef:null,inputs:Zy(n.inputs,t),outputs:Gy(n.outputs),debugInfo:null}}function bd(n){n.features?.forEach(t=>t(n))}function Ll(n,t){return n?()=>{const e=typeof n=="function"?n():n,r=[];for(const i of e){const o=t(i);o!==null&&r.push(o)}return r}:null}function $y(n){let t=0;const e=typeof n.consts=="function"?"":n.consts,r=[n.selectors,n.ngContentSelectors,n.hostVars,n.hostAttrs,e,n.vars,n.decls,n.encapsulation,n.standalone,n.signals,n.exportAs,JSON.stringify(n.inputs),JSON.stringify(n.outputs),Object.getOwnPropertyNames(n.type.prototype),!!n.contentQueries,!!n.viewQuery];for(const o of r.join("|"))t=Math.imul(31,t)+o.charCodeAt(0)<<0;return t+=2147483648,"c"+t}function Wy(n,t,e,r,i,o,s,a){if(e.firstCreatePass){n.mergedAttrs=xi(n.mergedAttrs,n.attrs);const c=n.tView=Ws(2,n,i,o,s,e.directiveRegistry,e.pipeRegistry,null,e.schemas,e.consts,null);e.queries!==null&&(e.queries.template(e,n),c.queries=e.queries.embeddedTView(n))}a&&(n.flags|=a),Er(n,!1);const l=Yy(e,t);Ls()&&Js(e,t,l,n),Ue(l,t);const u=ed(l,t,l,n);t[r+qn]=u,Xs(t,u)}function sa(n,t,e,r,i,o,s,a,l,u,c){const f=e+qn;let b;if(t.firstCreatePass){if(b=Mi(t,f,4,s||null,a||null),u!=null){const p=oe(t.consts,u);b.localNames=[];for(let E=0;E<p.length;E+=2)b.localNames.push(p[E],-1)}}else b=t.data[f];return Wy(b,n,t,e,r,i,o,l),u!=null&&na(n,b,c),b}let Yy=Xy;function Xy(n,t,e,r){return Ns(!0),t[zn].createComment("")}const qy=new on("");function aa(n){return!!n&&typeof n.then=="function"}function _d(n){return!!n&&typeof n.subscribe=="function"}const Qy=new on("");let Dd=(()=>{class n{resolve;reject;initialized=!1;done=!1;donePromise=new Promise((e,r)=>{this.resolve=e,this.reject=r});appInits=z(Qy,{optional:!0})??[];injector=z(qe);constructor(){}runInitializers(){if(this.initialized)return;const e=[];for(const i of this.appInits){const o=Ss(this.injector,i);if(aa(o))e.push(o);else if(_d(o)){const s=new Promise((a,l)=>{o.subscribe({complete:a,error:l})});e.push(s)}}const r=()=>{this.done=!0,this.resolve()};Promise.all(e).then(()=>{r()}).catch(i=>{this.reject(i)}),e.length===0&&r(),this.initialized=!0}static ɵfac=function(r){return new(r||n)};static ɵprov=fn({token:n,factory:n.ɵfac,providedIn:"root"})}return n})();const Jy=new on("");function Ky(){Fh(()=>{let n="";throw new V(600,n)})}function n1(n){return n.isBoundToModule}const t1=10;let qo=(()=>{class n{_runningTick=!1;_destroyed=!1;_destroyListeners=[];_views=[];internalErrorHandler=z(Qe);afterRenderManager=z(ng);zonelessEnabled=z(zs);rootEffectScheduler=z(Y0);dirtyFlags=0;tracingSnapshot=null;allTestViews=new Set;autoDetectTestViews=new Set;includeAllTestViews=!1;afterTick=new _i;get allViews(){return[...(this.includeAllTestViews?this.allTestViews:this.autoDetectTestViews).keys(),...this._views]}get destroyed(){return this._destroyed}componentTypes=[];components=[];internalPendingTask=z(Cr);get isStable(){return this.internalPendingTask.hasPendingTasksObservable.pipe(Te(e=>!e))}constructor(){z(Ai,{optional:!0})}whenStable(){let e;return new Promise(r=>{e=this.isStable.subscribe({next:i=>{i&&r()}})}).finally(()=>{e.unsubscribe()})}_injector=z(Vt);_rendererFactory=null;get injector(){return this._injector}bootstrap(e,r){return this.bootstrapImpl(e,r)}bootstrapImpl(e,r,i=qe.NULL){return this._injector.get(St).run(()=>{En(mn.BootstrapComponentStart);const s=e instanceof ad;if(!this._injector.get(Dd).done){let E="";throw new V(405,E)}let l;s?l=e:l=this._injector.get(Li).resolveComponentFactory(e),this.componentTypes.push(l.componentType);const u=n1(l)?void 0:this._injector.get(Ve),c=r||l.selector,f=l.create(i,[],c,u),b=f.location.nativeElement,p=f.injector.get(qy,null);return p?.registerApplication(b),f.onDestroy(()=>{this.detachView(f.hostView),Qr(this.components,f),p?.unregisterApplication(b)}),this._loadComponent(f),En(mn.BootstrapComponentEnd,f),f})}tick(){this.zonelessEnabled||(this.dirtyFlags|=1),this._tick()}_tick(){En(mn.ChangeDetectionStart),this.tracingSnapshot!==null?this.tracingSnapshot.run(jf.CHANGE_DETECTION,this.tickImpl):this.tickImpl()}tickImpl=()=>{if(this._runningTick)throw En(mn.ChangeDetectionEnd),new V(101,!1);const e=X(null);try{this._runningTick=!0,this.synchronize()}finally{this._runningTick=!1,this.tracingSnapshot?.dispose(),this.tracingSnapshot=null,X(e),this.afterTick.next(),En(mn.ChangeDetectionEnd)}};synchronize(){this._rendererFactory===null&&!this._injector.destroyed&&(this._rendererFactory=this._injector.get(ia,null,{optional:!0}));let e=0;for(;this.dirtyFlags!==0&&e++<t1;){En(mn.ChangeDetectionSyncStart);try{this.synchronizeOnce()}finally{En(mn.ChangeDetectionSyncEnd)}}}synchronizeOnce(){this.dirtyFlags&16&&(this.dirtyFlags&=-17,this.rootEffectScheduler.flush());let e=!1;if(this.dirtyFlags&7){const r=!!(this.dirtyFlags&1);this.dirtyFlags&=-8,this.dirtyFlags|=8;for(let{_lView:i}of this.allViews){if(!r&&!si(i))continue;const o=r&&!this.zonelessEnabled?0:1;Jf(i,o),e=!0}if(this.dirtyFlags&=-5,this.syncDirtyFlagsWithViews(),this.dirtyFlags&23)return}e||(this._rendererFactory?.begin?.(),this._rendererFactory?.end?.()),this.dirtyFlags&8&&(this.dirtyFlags&=-9,this.afterRenderManager.execute()),this.syncDirtyFlagsWithViews()}syncDirtyFlagsWithViews(){if(this.allViews.some(({_lView:e})=>si(e))){this.dirtyFlags|=2;return}else this.dirtyFlags&=-8}attachView(e){const r=e;this._views.push(r),r.attachToAppRef(this)}detachView(e){const r=e;Qr(this._views,r),r.detachFromAppRef()}_loadComponent(e){this.attachView(e.hostView);try{this.tick()}catch(i){this.internalErrorHandler(i)}this.components.push(e),this._injector.get(Jy,[]).forEach(i=>i(e))}ngOnDestroy(){if(!this._destroyed)try{this._destroyListeners.forEach(e=>e()),this._views.slice().forEach(e=>e.destroy())}finally{this._destroyed=!0,this._views=[],this._destroyListeners=[]}}onDestroy(e){return this._destroyListeners.push(e),()=>Qr(this._destroyListeners,e)}destroy(){if(this._destroyed)throw new V(406,!1);const e=this._injector;e.destroy&&!e.destroyed&&e.destroy()}get viewCount(){return this._views.length}static ɵfac=function(r){return new(r||n)};static ɵprov=fn({token:n,factory:n.ɵfac,providedIn:"root"})}return n})();function Qr(n,t){const e=n.indexOf(t);e>-1&&n.splice(e,1)}class e1{destroy(t){}updateValue(t,e){}swap(t,e){const r=Math.min(t,e),i=Math.max(t,e),o=this.detach(i);if(i-r>1){const s=this.detach(r);this.attach(r,o),this.attach(i,s)}else this.attach(r,o)}move(t,e){this.attach(e,this.detach(t))}}function eo(n,t,e,r,i){return n===e&&Object.is(t,r)?1:Object.is(i(n,t),i(e,r))?-1:0}function r1(n,t,e,r){let i,o,s=0,a=n.length-1;if(Array.isArray(t)){X(r);let l=t.length-1;for(X(null);s<=a&&s<=l;){const u=n.at(s),c=t[s],f=eo(s,u,s,c,e);if(f!==0){f<0&&n.updateValue(s,c),s++;continue}const b=n.at(a),p=t[l],E=eo(a,b,l,p,e);if(E!==0){E<0&&n.updateValue(a,p),a--,l--;continue}const A=e(s,u),F=e(a,b),S=e(s,c);if(Object.is(S,F)){const gn=e(l,p);Object.is(gn,A)?(n.swap(s,a),n.updateValue(a,p),l--,a--):n.move(a,s),n.updateValue(s,c),s++;continue}if(i??=new jl,o??=Bl(n,s,a,e),Qo(n,i,s,S))n.updateValue(s,c),s++,a++;else if(o.has(S))i.set(A,n.detach(s)),a--;else{const gn=n.create(s,t[s]);n.attach(s,gn),s++,a++}}for(;s<=l;)Nl(n,i,e,s,t[s]),s++}else if(t!=null){X(r);const l=t[Symbol.iterator]();X(null);let u=l.next();for(;!u.done&&s<=a;){const c=n.at(s),f=u.value,b=eo(s,c,s,f,e);if(b!==0)b<0&&n.updateValue(s,f),s++,u=l.next();else{i??=new jl,o??=Bl(n,s,a,e);const p=e(s,f);if(Qo(n,i,s,p))n.updateValue(s,f),s++,a++,u=l.next();else if(!o.has(p))n.attach(s,n.create(s,f)),s++,a++,u=l.next();else{const E=e(s,c);i.set(E,n.detach(s)),a--}}}for(;!u.done;)Nl(n,i,e,n.length,u.value),u=l.next()}for(;s<=a;)n.destroy(n.detach(a--));i?.forEach(l=>{n.destroy(l)})}function Qo(n,t,e,r){return t!==void 0&&t.has(r)?(n.attach(e,t.get(r)),t.delete(r),!0):!1}function Nl(n,t,e,r,i){if(Qo(n,t,r,e(r,i)))n.updateValue(r,i);else{const o=n.create(r,i);n.attach(r,o)}}function Bl(n,t,e,r){const i=new Set;for(let o=t;o<=e;o++)i.add(r(o,n.at(o)));return i}class jl{kvMap=new Map;_vMap=void 0;has(t){return this.kvMap.has(t)}delete(t){if(!this.has(t))return!1;const e=this.kvMap.get(t);return this._vMap!==void 0&&this._vMap.has(e)?(this.kvMap.set(t,this._vMap.get(e)),this._vMap.delete(e)):this.kvMap.delete(t),!0}get(t){return this.kvMap.get(t)}set(t,e){if(this.kvMap.has(t)){let r=this.kvMap.get(t);this._vMap===void 0&&(this._vMap=new Map);const i=this._vMap;for(;i.has(r);)r=i.get(r);i.set(r,e)}else this.kvMap.set(t,e)}forEach(t){for(let[e,r]of this.kvMap)if(t(r,e),this._vMap!==void 0){const i=this._vMap;for(;i.has(r);)r=i.get(r),t(r,e)}}}function Jo(n,t,e,r,i,o,s,a){xr("NgControlFlow");const l=Fn(),u=Ct(),c=oe(u.consts,o);return sa(l,u,n,t,e,r,i,c,256,s,a),Ed}function Ed(n,t,e,r,i,o,s,a){xr("NgControlFlow");const l=Fn(),u=Ct(),c=oe(u.consts,o);return sa(l,u,n,t,e,r,i,c,512,s,a),Ed}function Ko(n,t){xr("NgControlFlow");const e=Fn(),r=Ci(),i=e[r]!==ae?e[r]:-1,o=i!==-1?mi(e,qn+i):void 0,s=0;if(Tr(e,r,n)){const a=X(null);try{if(o!==void 0&&id(o,s),n!==-1){const l=qn+n,u=mi(e,l),c=ns(e[Y],l),f=sd(u,c,e),b=Oi(e,c,t,{dehydratedView:f});Pi(u,b,s,vr(c,f))}}finally{X(a)}}else if(o!==void 0){const a=rd(o,s);a!==void 0&&(a[Zn]=t)}}class i1{lContainer;$implicit;$index;constructor(t,e,r){this.lContainer=t,this.$implicit=e,this.$index=r}get $count(){return this.lContainer.length-jn}}function o1(n,t){return t}class s1{hasEmptyBlock;trackByFn;liveCollection;constructor(t,e,r){this.hasEmptyBlock=t,this.trackByFn=e,this.liveCollection=r}}function ro(n,t,e,r,i,o,s,a,l,u,c,f,b){xr("NgControlFlow");const p=Fn(),E=Ct(),A=l!==void 0,F=Fn(),S=s,gn=new s1(A,S);F[qn+n]=gn,sa(p,E,n+1,t,e,r,i,oe(E.consts,o),256)}class a1 extends e1{lContainer;hostLView;templateTNode;operationsCounter=void 0;needsIndexUpdate=!1;constructor(t,e,r){super(),this.lContainer=t,this.hostLView=e,this.templateTNode=r}get length(){return this.lContainer.length-jn}at(t){return this.getLView(t)[Zn].$implicit}attach(t,e){const r=e[gr];this.needsIndexUpdate||=t!==this.length,Pi(this.lContainer,e,t,vr(this.templateTNode,r)),l1(this.lContainer,t)}detach(t){return this.needsIndexUpdate||=t!==this.length-1,u1(this.lContainer,t),c1(this.lContainer,t)}create(t,e){const r=Yo(this.lContainer,this.templateTNode.tView.ssrId);return Oi(this.hostLView,this.templateTNode,new i1(this.lContainer,e,t),{dehydratedView:r})}destroy(t){Fi(t[Y],t)}updateValue(t,e){this.getLView(t)[Zn].$implicit=e}reset(){this.needsIndexUpdate=!1}updateIndexes(){if(this.needsIndexUpdate)for(let t=0;t<this.length;t++)this.getLView(t)[Zn].$index=t}getLView(t){return f1(this.lContainer,t)}}function io(n){const t=X(null),e=De();try{const r=Fn(),i=r[Y],o=r[e],s=e+1,a=mi(r,s);if(o.liveCollection===void 0){const u=ns(i,s);o.liveCollection=new a1(a,r,u)}else o.liveCollection.reset();const l=o.liveCollection;if(r1(l,n,o.trackByFn,t),l.updateIndexes(),o.hasEmptyBlock){const u=Ci(),c=l.length===0;if(Tr(r,u,c)){const f=e+2,b=mi(r,f);if(c){const p=ns(i,f),E=sd(b,p,r),A=Oi(r,p,void 0,{dehydratedView:E});Pi(b,A,0,vr(p,E))}else i.firstUpdatePass&&Jg(b),id(b,0)}}}finally{X(t)}}function mi(n,t){return n[t]}function l1(n,t){if(n.length<=jn)return;const e=jn+t,r=n[e],i=r?r[Gt]:void 0;if(r&&i&&i.detachedLeaveAnimationFns&&i.detachedLeaveAnimationFns.length>0){const o=r[re];tg(o,i),ze.delete(r[Zt]),i.detachedLeaveAnimationFns=void 0}}function u1(n,t){if(n.length<=jn)return;const e=jn+t,r=n[e],i=r?r[Gt]:void 0;i&&i.leave&&i.leave.size>0&&(i.detachedLeaveAnimationFns=[])}function c1(n,t){return wr(n,t)}function f1(n,t){return rd(n,t)}function ns(n,t){return Rs(n,t)}function la(n,t,e){const r=Fn(),i=Ci();if(Tr(r,i,t)){Ct();const o=A0();_g(o,r,n,t,r[zn],e)}return la}function Hl(n,t,e,r,i){ta(t,n,e,i?"class":"style",r)}function dn(n,t,e,r){const i=Fn(),o=i[Y],s=n+qn,a=o.firstCreatePass?cd(s,i,2,t,Tg,y0(),e,r):o.data[s];if($f(a,i,n,t,kd),Fs(a)){const l=i[Y];Gf(l,i,a),Sf(l,a,i)}return r!=null&&na(i,a),dn}function un(){const n=Ct(),t=ft(),e=Wf(t);return n.firstCreatePass&&fd(n,e),Bc(e)&&jc(),Nc(),e.classesWithoutHost!=null&&im(e)&&Hl(n,e,Fn(),e.classesWithoutHost,!0),e.stylesWithoutHost!=null&&om(e)&&Hl(n,e,Fn(),e.stylesWithoutHost,!1),un}function ua(n,t,e,r){return dn(n,t,e,r),un(),ua}function ca(n,t,e,r){const i=Fn(),o=i[Y],s=n+qn,a=o.firstCreatePass?yy(s,o,2,t,e,r):o.data[s];return $f(a,i,n,t,kd),r!=null&&na(i,a),ca}function fa(){const n=ft(),t=Wf(n);return Bc(t)&&jc(),Nc(),fa}function Cd(n,t,e,r){return ca(n,t,e,r),fa(),Cd}let kd=(n,t,e,r,i)=>(Ns(!0),Ff(t[zn],r,S0()));const er=void 0;function d1(n){const t=Math.floor(Math.abs(n)),e=n.toString().replace(/^[^.]*\.?/,"").length;return t===1&&e===0?1:5}var h1=["en",[["a","p"],["AM","PM"]],[["AM","PM"]],[["S","M","T","W","T","F","S"],["Sun","Mon","Tue","Wed","Thu","Fri","Sat"],["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"],["Su","Mo","Tu","We","Th","Fr","Sa"]],er,[["J","F","M","A","M","J","J","A","S","O","N","D"],["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"],["January","February","March","April","May","June","July","August","September","October","November","December"]],er,[["B","A"],["BC","AD"],["Before Christ","Anno Domini"]],0,[6,0],["M/d/yy","MMM d, y","MMMM d, y","EEEE, MMMM d, y"],["h:mm a","h:mm:ss a","h:mm:ss a z","h:mm:ss a zzzz"],["{1}, {0}",er,er,er],[".",",",";","%","+","-","E","×","‰","∞","NaN",":"],["#,##0.###","#,##0%","¤#,##0.00","#E0"],"USD","$","US Dollar",{},"ltr",d1];let oo={};function nt(n){const t=m1(n);let e=Ul(t);if(e)return e;const r=t.split("-")[0];if(e=Ul(r),e)return e;if(r==="en")return h1;throw new V(701,!1)}function p1(n){return nt(n)[Mn.PluralCase]}function Ul(n){return n in oo||(oo[n]=Lr.ng&&Lr.ng.common&&Lr.ng.common.locales&&Lr.ng.common.locales[n]),oo[n]}var Mn=(function(n){return n[n.LocaleId=0]="LocaleId",n[n.DayPeriodsFormat=1]="DayPeriodsFormat",n[n.DayPeriodsStandalone=2]="DayPeriodsStandalone",n[n.DaysFormat=3]="DaysFormat",n[n.DaysStandalone=4]="DaysStandalone",n[n.MonthsFormat=5]="MonthsFormat",n[n.MonthsStandalone=6]="MonthsStandalone",n[n.Eras=7]="Eras",n[n.FirstDayOfWeek=8]="FirstDayOfWeek",n[n.WeekendRange=9]="WeekendRange",n[n.DateFormat=10]="DateFormat",n[n.TimeFormat=11]="TimeFormat",n[n.DateTimeFormat=12]="DateTimeFormat",n[n.NumberSymbols=13]="NumberSymbols",n[n.NumberFormats=14]="NumberFormats",n[n.CurrencyCode=15]="CurrencyCode",n[n.CurrencySymbol=16]="CurrencySymbol",n[n.CurrencyName=17]="CurrencyName",n[n.Currencies=18]="Currencies",n[n.Directionality=19]="Directionality",n[n.PluralCase=20]="PluralCase",n[n.ExtraData=21]="ExtraData",n})(Mn||{});function m1(n){return n.toLowerCase().replace(/_/g,"-")}const ts="en-US",g1="USD";function y1(n){typeof n=="string"&&n.toLowerCase().replace(/_/g,"-")}function ke(n,t,e){const r=Fn(),i=Ct(),o=ft();return v1(i,r,r[zn],o,n,t,e),ke}function v1(n,t,e,r,i,o,s){let a=!0,l=null;if((r.type&3||s)&&(l??=no(r,t,o),wy(r,n,t,s,e,i,o,l)&&(a=!1)),a){const u=r.outputs?.[i],c=r.hostDirectiveOutputs?.[i];if(c&&c.length)for(let f=0;f<c.length;f+=2){const b=c[f],p=c[f+1];l??=no(r,t,o),Ol(r,t,b,p,i,l)}if(u&&u.length)for(const f of u)l??=no(r,t,o),Ol(r,t,f,i,i,l)}}function xd(n=1){return I0(n)}function Nr(n,t){return n<<17|t<<2}function ve(n){return n>>17&32767}function w1(n){return(n&2)==2}function b1(n,t){return n&131071|t<<17}function es(n){return n|2}function Ze(n){return(n&131068)>>2}function so(n,t){return n&-131069|t<<2}function _1(n){return(n&1)===1}function rs(n){return n|1}function D1(n,t,e,r,i,o){let s=o?t.classBindings:t.styleBindings,a=ve(s),l=Ze(s);n[r]=e;let u=!1,c;if(Array.isArray(e)){const f=e;c=f[1],(c===null||Dr(f,c)>0)&&(u=!0)}else c=e;if(i)if(l!==0){const b=ve(n[a+1]);n[r+1]=Nr(b,a),b!==0&&(n[b+1]=so(n[b+1],r)),n[a+1]=b1(n[a+1],r)}else n[r+1]=Nr(a,0),a!==0&&(n[a+1]=so(n[a+1],r)),a=r;else n[r+1]=Nr(l,0),a===0?a=r:n[l+1]=so(n[l+1],r),l=r;u&&(n[r+1]=es(n[r+1])),zl(n,c,r,!0),zl(n,c,r,!1),E1(t,c,n,r,o),s=Nr(a,l),o?t.classBindings=s:t.styleBindings=s}function E1(n,t,e,r,i){const o=i?n.residualClasses:n.residualStyles;o!=null&&typeof t=="string"&&Dr(o,t)>=0&&(e[r+1]=rs(e[r+1]))}function zl(n,t,e,r){const i=n[e+1],o=t===null;let s=r?ve(i):Ze(i),a=!1;for(;s!==0&&(a===!1||o);){const l=n[s],u=n[s+1];C1(l,t)&&(a=!0,n[s+1]=r?rs(u):es(u)),s=r?ve(u):Ze(u)}a&&(n[e+1]=r?es(i):rs(i))}function C1(n,t){return n===null||t==null||(Array.isArray(n)?n[1]:n)===t?!0:Array.isArray(n)&&typeof t=="string"?Dr(n,t)>=0:!1}function Td(n,t,e){return Id(n,t,e,!1),Td}function da(n,t){return Id(n,t,null,!0),da}function Id(n,t,e,r){const i=Fn(),o=Ct(),s=D0(2);if(o.firstUpdatePass&&x1(o,n,s,r),t!==ae&&Tr(i,s,t)){const a=o.data[De()];F1(o,a,i,i[zn],n,i[s+1]=R1(t,e),r,s)}}function k1(n,t){return t>=n.expandoStartIndex}function x1(n,t,e,r){const i=n.data;if(i[e+1]===null){const o=i[De()],s=k1(n,e);O1(o,r)&&t===null&&!s&&(t=!1),t=T1(i,o,t,r),D1(i,o,t,e,s,r)}}function T1(n,t,e,r){const i=x0(n);let o=r?t.residualClasses:t.residualStyles;if(i===null)(r?t.classBindings:t.styleBindings)===0&&(e=ao(null,n,t,e,r),e=_r(e,t.attrs,r),o=null);else{const s=t.directiveStylingLast;if(s===-1||n[s]!==i)if(e=ao(i,n,t,e,r),o===null){let l=I1(n,t,r);l!==void 0&&Array.isArray(l)&&(l=ao(null,n,t,l[1],r),l=_r(l,t.attrs,r),A1(n,t,r,l))}else o=S1(n,t,r)}return o!==void 0&&(r?t.residualClasses=o:t.residualStyles=o),e}function I1(n,t,e){const r=e?t.classBindings:t.styleBindings;if(Ze(r)!==0)return n[ve(r)]}function A1(n,t,e,r){const i=e?t.classBindings:t.styleBindings;n[ve(i)]=r}function S1(n,t,e){let r;const i=t.directiveEnd;for(let o=1+t.directiveStylingLast;o<i;o++){const s=n[o].hostAttrs;r=_r(r,s,e)}return _r(r,t.attrs,e)}function ao(n,t,e,r,i){let o=null;const s=e.directiveEnd;let a=e.directiveStylingLast;for(a===-1?a=e.directiveStart:a++;a<s&&(o=t[a],r=_r(r,o.hostAttrs,i),o!==n);)a++;return n!==null&&(e.directiveStylingLast=a),r}function _r(n,t,e){const r=e?1:2;let i=-1;if(t!==null)for(let o=0;o<t.length;o++){const s=t[o];typeof s=="number"?i=s:i===r&&(Array.isArray(n)||(n=n===void 0?[]:["",n]),Yp(n,s,e?!0:t[++o]))}return n===void 0?null:n}function F1(n,t,e,r,i,o,s,a){if(!(t.type&3))return;const l=n.data,u=l[a+1],c=_1(u)?Vl(l,t,e,i,Ze(u),s):void 0;if(!gi(c)){gi(o)||w1(u)&&(o=Vl(l,null,e,i,a,s));const f=Oc(De(),e);vg(r,s,f,i,o)}}function Vl(n,t,e,r,i,o){const s=t===null;let a;for(;i>0;){const l=n[i],u=Array.isArray(l),c=u?l[1]:l,f=c===null;let b=e[i+1];b===ae&&(b=f?wt:void 0);let p=f?$i(b,r):c===r?b:void 0;if(u&&!gi(p)&&(p=$i(l,r)),gi(p)&&(a=p,s))return a;const E=n[i+1];i=s?ve(E):Ze(E)}if(t!==null){let l=o?t.residualClasses:t.residualStyles;l!=null&&(a=$i(l,r))}return a}function gi(n){return n!==void 0}function R1(n,t){return n==null||n===""||(typeof t=="string"?n=n+t:typeof n=="object"&&(n=bs(Fm(n)))),n}function O1(n,t){return(n.flags&(t?8:16))!==0}function An(n,t=""){const e=Fn(),r=Ct(),i=n+qn,o=r.firstCreatePass?Mi(r,i,1,t,null):r.data[i],s=P1(r,e,o,t);e[i]=s,Ls()&&Js(r,e,s,o),Er(o,!1)}let P1=(n,t,e,r)=>(Ns(!0),Rm(t[zn],r));function M1(n,t,e,r=""){return Tr(n,Ci(),e)?t+Bp(e)+r:ae}function bt(n){return ha("",n),bt}function ha(n,t,e){const r=Fn(),i=M1(r,n,t,e);return i!==ae&&L1(r,De(),i),ha}function L1(n,t,e){const r=Oc(t,n);Om(n[zn],r,e)}function N1(n,t,e){const r=Ct();r.firstCreatePass&&Ad(t,r.data,r.blueprint,_e(n),e)}function Ad(n,t,e,r,i){if(n=Jn(n),Array.isArray(n))for(let o=0;o<n.length;o++)Ad(n[o],t,e,r,i);else{const o=Ct(),s=Fn(),a=ft();let l=Be(n)?n:Jn(n.provide);const u=Ac(n),c=a.providerIndexes&1048575,f=a.directiveStart,b=a.providerIndexes>>20;if(Be(n)||!n.multi){const p=new kr(u,i,an,null),E=uo(l,t,c+b,f);E===-1?(jo(fi(a,s),o,l),lo(o,n,t.length),t.push(l),a.directiveStart++,a.directiveEnd++,e.push(p),s.push(p)):(e[E]=p,s[E]=p)}else{const p=uo(l,t,c+b,f),E=uo(l,t,c,c+b),A=p>=0&&e[p],F=E>=0&&e[E];if(A){const S=Sd(e[p],u,r);lo(o,n,p>-1?p:E,S)}else{jo(fi(a,s),o,l);const S=H1(B1,e.length,i,r,u);F&&(e[E].providerFactory=S),lo(o,n,t.length,0),t.push(l),a.directiveStart++,a.directiveEnd++,e.push(S),s.push(S)}r&&F&&e[E].componentProviders++}}}function lo(n,t,e,r){const i=Be(t),o=t0(t);if(i||o){const l=(o?Jn(t.useClass):t).prototype.ngOnDestroy;if(l){const u=n.destroyHooks||(n.destroyHooks=[]);if(!i&&t.multi){const c=u.indexOf(e);c===-1?u.push(e,[r,l]):u[c+1].push(r,l)}else u.push(e,l)}}}function Sd(n,t,e){return e&&n.componentProviders++,n.multi.push(t)-1}function uo(n,t,e,r){for(let i=e;i<r;i++)if(t[i]===n)return i;return-1}function B1(n,t,e,r,i){return j1(this.multi,[])}function j1(n,t){for(let e=0;e<n.length;e++){const r=n[e];t.push(r())}return t}function H1(n,t,e,r,i,o){const s=new kr(n,e,an,null);return s.multi=[],s.index=t,s.componentProviders=0,Sd(s,i,r&&!e),s}function U1(n,t){return e=>{e.providersResolver=(r,i)=>N1(r,i?i(n):n,!1)}}let z1=(()=>{class n{applicationErrorHandler=z(Qe);appRef=z(qo);taskService=z(Cr);ngZone=z(St);zonelessEnabled=z(zs);tracing=z(Ai,{optional:!0});zoneIsDefined=typeof Zone<"u"&&!!Zone.root.run;schedulerTickApplyArgs=[{data:{__scheduler_tick__:!0}}];subscriptions=new We;angularZoneId=this.zoneIsDefined?this.ngZone._inner?.get(li):null;scheduleInRootZone=!this.zonelessEnabled&&this.zoneIsDefined&&(z($0,{optional:!0})??!1);cancelScheduledCallback=null;useMicrotaskScheduler=!1;runningTick=!1;pendingRenderTaskId=null;constructor(){this.subscriptions.add(this.appRef.afterTick.subscribe(()=>{const e=this.taskService.add();if(!this.runningTick&&(this.cleanup(),!this.zonelessEnabled||this.appRef.includeAllTestViews)){this.taskService.remove(e);return}this.switchToMicrotaskScheduler(),this.taskService.remove(e)})),this.subscriptions.add(this.ngZone.onUnstable.subscribe(()=>{this.runningTick||this.cleanup()}))}switchToMicrotaskScheduler(){this.ngZone.runOutsideAngular(()=>{const e=this.taskService.add();this.useMicrotaskScheduler=!0,queueMicrotask(()=>{this.useMicrotaskScheduler=!1,this.taskService.remove(e)})})}notify(e){if(!this.zonelessEnabled&&e===5)return;switch(e){case 0:{this.appRef.dirtyFlags|=2;break}case 3:case 2:case 4:case 5:case 1:{this.appRef.dirtyFlags|=4;break}case 6:{this.appRef.dirtyFlags|=2;break}case 12:{this.appRef.dirtyFlags|=16;break}case 13:{this.appRef.dirtyFlags|=2;break}case 11:break;default:this.appRef.dirtyFlags|=8}if(this.appRef.tracingSnapshot=this.tracing?.snapshot(this.appRef.tracingSnapshot)??null,!this.shouldScheduleTick())return;const r=this.useMicrotaskScheduler?L0:Qc;this.pendingRenderTaskId=this.taskService.add(),this.scheduleInRootZone?this.cancelScheduledCallback=Zone.root.run(()=>r(()=>this.tick())):this.cancelScheduledCallback=this.ngZone.runOutsideAngular(()=>r(()=>this.tick()))}shouldScheduleTick(){return!(this.appRef.destroyed||this.pendingRenderTaskId!==null||this.runningTick||this.appRef._runningTick||!this.zonelessEnabled&&this.zoneIsDefined&&Zone.current.get(li+this.angularZoneId))}tick(){if(this.runningTick||this.appRef.destroyed)return;if(this.appRef.dirtyFlags===0){this.cleanup();return}!this.zonelessEnabled&&this.appRef.dirtyFlags&7&&(this.appRef.dirtyFlags|=1);const e=this.taskService.add();try{this.ngZone.run(()=>{this.runningTick=!0,this.appRef._tick()},void 0,this.schedulerTickApplyArgs)}catch(r){this.applicationErrorHandler(r)}finally{this.taskService.remove(e),this.cleanup()}}ngOnDestroy(){this.subscriptions.unsubscribe(),this.cleanup()}cleanup(){if(this.runningTick=!1,this.cancelScheduledCallback?.(),this.cancelScheduledCallback=null,this.pendingRenderTaskId!==null){const e=this.pendingRenderTaskId;this.pendingRenderTaskId=null,this.taskService.remove(e)}}static ɵfac=function(r){return new(r||n)};static ɵprov=fn({token:n,factory:n.ɵfac,providedIn:"root"})}return n})();function V1(){return[{provide:Us,useExisting:z1},{provide:St,useClass:U0},{provide:zs,useValue:!0}]}function Z1(){return typeof $localize<"u"&&$localize.locale||ts}const ue=new on("",{factory:()=>z(ue,{optional:!0,skipSelf:!0})||Z1()}),G1=new on("",{factory:()=>g1});function Zl(n){return Ep(n)}const is=new on(""),$1=new on("");function rr(n){return!n.moduleRef}function W1(n){const t=rr(n)?n.r3Injector:n.moduleRef.injector,e=t.get(St);return e.run(()=>{rr(n)?n.r3Injector.resolveInjectorInitializers():n.moduleRef.resolveInjectorInitializers();const r=t.get(Qe);let i;if(e.runOutsideAngular(()=>{i=e.onError.subscribe({next:r})}),rr(n)){const o=()=>t.destroy(),s=n.platformInjector.get(is);s.add(o),t.onDestroy(()=>{i.unsubscribe(),s.delete(o)})}else{const o=()=>n.moduleRef.destroy(),s=n.platformInjector.get(is);s.add(o),n.moduleRef.onDestroy(()=>{Qr(n.allPlatformModules,n.moduleRef),i.unsubscribe(),s.delete(o)})}return X1(r,e,()=>{const o=t.get(Cr),s=o.add(),a=t.get(Dd);return a.runInitializers(),a.donePromise.then(()=>{const l=t.get(ue,ts);if(y1(l||ts),!t.get($1,!0))return rr(n)?t.get(qo):(n.allPlatformModules.push(n.moduleRef),n.moduleRef);if(rr(n)){const c=t.get(qo);return n.rootComponent!==void 0&&c.bootstrap(n.rootComponent),c}else return Y1?.(n.moduleRef,n.allPlatformModules),n.moduleRef}).finally(()=>{o.remove(s)})})})}let Y1;function X1(n,t,e){try{const r=e();return aa(r)?r.catch(i=>{throw t.runOutsideAngular(()=>n(i)),i}):r}catch(r){throw t.runOutsideAngular(()=>n(r)),r}}let Jr=null;function q1(n=[],t){return qe.create({name:t,providers:[{provide:Ts,useValue:"platform"},{provide:is,useValue:new Set([()=>Jr=null])},...n]})}function Q1(n=[]){if(Jr)return Jr;const t=q1(n);return Jr=t,Ky(),J1(t),t}function J1(n){const t=n.get(Ef,null);Ss(n,()=>{t?.forEach(e=>e())})}let K1=(()=>{class n{static __NG_ELEMENT_ID__=nv}return n})();function nv(n){return tv(ft(),Fn(),(n&16)===16)}function tv(n,t,e){if(be(n)&&!e){const r=Dt(n.index,t);return new br(r,r)}else if(n.type&175){const r=t[It];return new br(r,t)}return null}class ev{supports(t){return dd(t)}create(t){return new iv(t)}}const rv=(n,t)=>t;class iv{length=0;collection;_linkedRecords=null;_unlinkedRecords=null;_previousItHead=null;_itHead=null;_itTail=null;_additionsHead=null;_additionsTail=null;_movesHead=null;_movesTail=null;_removalsHead=null;_removalsTail=null;_identityChangesHead=null;_identityChangesTail=null;_trackByFn;constructor(t){this._trackByFn=t||rv}forEachItem(t){let e;for(e=this._itHead;e!==null;e=e._next)t(e)}forEachOperation(t){let e=this._itHead,r=this._removalsHead,i=0,o=null;for(;e||r;){const s=!r||e&&e.currentIndex<$l(r,i,o)?e:r,a=$l(s,i,o),l=s.currentIndex;if(s===r)i--,r=r._nextRemoved;else if(e=e._next,s.previousIndex==null)i++;else{o||(o=[]);const u=a-i,c=l-i;if(u!=c){for(let b=0;b<u;b++){const p=b<o.length?o[b]:o[b]=0,E=p+b;c<=E&&E<u&&(o[b]=p+1)}const f=s.previousIndex;o[f]=c-u}}a!==l&&t(s,a,l)}}forEachPreviousItem(t){let e;for(e=this._previousItHead;e!==null;e=e._nextPrevious)t(e)}forEachAddedItem(t){let e;for(e=this._additionsHead;e!==null;e=e._nextAdded)t(e)}forEachMovedItem(t){let e;for(e=this._movesHead;e!==null;e=e._nextMoved)t(e)}forEachRemovedItem(t){let e;for(e=this._removalsHead;e!==null;e=e._nextRemoved)t(e)}forEachIdentityChange(t){let e;for(e=this._identityChangesHead;e!==null;e=e._nextIdentityChange)t(e)}diff(t){if(t==null&&(t=[]),!dd(t))throw new V(900,!1);return this.check(t)?this:null}onDestroy(){}check(t){this._reset();let e=this._itHead,r=!1,i,o,s;if(Array.isArray(t)){this.length=t.length;for(let a=0;a<this.length;a++)o=t[a],s=this._trackByFn(a,o),e===null||!Object.is(e.trackById,s)?(e=this._mismatch(e,o,s,a),r=!0):(r&&(e=this._verifyReinsertion(e,o,s,a)),Object.is(e.item,o)||this._addIdentityChange(e,o)),e=e._next}else i=0,vy(t,a=>{s=this._trackByFn(i,a),e===null||!Object.is(e.trackById,s)?(e=this._mismatch(e,a,s,i),r=!0):(r&&(e=this._verifyReinsertion(e,a,s,i)),Object.is(e.item,a)||this._addIdentityChange(e,a)),e=e._next,i++}),this.length=i;return this._truncate(e),this.collection=t,this.isDirty}get isDirty(){return this._additionsHead!==null||this._movesHead!==null||this._removalsHead!==null||this._identityChangesHead!==null}_reset(){if(this.isDirty){let t;for(t=this._previousItHead=this._itHead;t!==null;t=t._next)t._nextPrevious=t._next;for(t=this._additionsHead;t!==null;t=t._nextAdded)t.previousIndex=t.currentIndex;for(this._additionsHead=this._additionsTail=null,t=this._movesHead;t!==null;t=t._nextMoved)t.previousIndex=t.currentIndex;this._movesHead=this._movesTail=null,this._removalsHead=this._removalsTail=null,this._identityChangesHead=this._identityChangesTail=null}}_mismatch(t,e,r,i){let o;return t===null?o=this._itTail:(o=t._prev,this._remove(t)),t=this._unlinkedRecords===null?null:this._unlinkedRecords.get(r,null),t!==null?(Object.is(t.item,e)||this._addIdentityChange(t,e),this._reinsertAfter(t,o,i)):(t=this._linkedRecords===null?null:this._linkedRecords.get(r,i),t!==null?(Object.is(t.item,e)||this._addIdentityChange(t,e),this._moveAfter(t,o,i)):t=this._addAfter(new ov(e,r),o,i)),t}_verifyReinsertion(t,e,r,i){let o=this._unlinkedRecords===null?null:this._unlinkedRecords.get(r,null);return o!==null?t=this._reinsertAfter(o,t._prev,i):t.currentIndex!=i&&(t.currentIndex=i,this._addToMoves(t,i)),t}_truncate(t){for(;t!==null;){const e=t._next;this._addToRemovals(this._unlink(t)),t=e}this._unlinkedRecords!==null&&this._unlinkedRecords.clear(),this._additionsTail!==null&&(this._additionsTail._nextAdded=null),this._movesTail!==null&&(this._movesTail._nextMoved=null),this._itTail!==null&&(this._itTail._next=null),this._removalsTail!==null&&(this._removalsTail._nextRemoved=null),this._identityChangesTail!==null&&(this._identityChangesTail._nextIdentityChange=null)}_reinsertAfter(t,e,r){this._unlinkedRecords!==null&&this._unlinkedRecords.remove(t);const i=t._prevRemoved,o=t._nextRemoved;return i===null?this._removalsHead=o:i._nextRemoved=o,o===null?this._removalsTail=i:o._prevRemoved=i,this._insertAfter(t,e,r),this._addToMoves(t,r),t}_moveAfter(t,e,r){return this._unlink(t),this._insertAfter(t,e,r),this._addToMoves(t,r),t}_addAfter(t,e,r){return this._insertAfter(t,e,r),this._additionsTail===null?this._additionsTail=this._additionsHead=t:this._additionsTail=this._additionsTail._nextAdded=t,t}_insertAfter(t,e,r){const i=e===null?this._itHead:e._next;return t._next=i,t._prev=e,i===null?this._itTail=t:i._prev=t,e===null?this._itHead=t:e._next=t,this._linkedRecords===null&&(this._linkedRecords=new Gl),this._linkedRecords.put(t),t.currentIndex=r,t}_remove(t){return this._addToRemovals(this._unlink(t))}_unlink(t){this._linkedRecords!==null&&this._linkedRecords.remove(t);const e=t._prev,r=t._next;return e===null?this._itHead=r:e._next=r,r===null?this._itTail=e:r._prev=e,t}_addToMoves(t,e){return t.previousIndex===e||(this._movesTail===null?this._movesTail=this._movesHead=t:this._movesTail=this._movesTail._nextMoved=t),t}_addToRemovals(t){return this._unlinkedRecords===null&&(this._unlinkedRecords=new Gl),this._unlinkedRecords.put(t),t.currentIndex=null,t._nextRemoved=null,this._removalsTail===null?(this._removalsTail=this._removalsHead=t,t._prevRemoved=null):(t._prevRemoved=this._removalsTail,this._removalsTail=this._removalsTail._nextRemoved=t),t}_addIdentityChange(t,e){return t.item=e,this._identityChangesTail===null?this._identityChangesTail=this._identityChangesHead=t:this._identityChangesTail=this._identityChangesTail._nextIdentityChange=t,t}}class ov{item;trackById;currentIndex=null;previousIndex=null;_nextPrevious=null;_prev=null;_next=null;_prevDup=null;_nextDup=null;_prevRemoved=null;_nextRemoved=null;_nextAdded=null;_nextMoved=null;_nextIdentityChange=null;constructor(t,e){this.item=t,this.trackById=e}}class sv{_head=null;_tail=null;add(t){this._head===null?(this._head=this._tail=t,t._nextDup=null,t._prevDup=null):(this._tail._nextDup=t,t._prevDup=this._tail,t._nextDup=null,this._tail=t)}get(t,e){let r;for(r=this._head;r!==null;r=r._nextDup)if((e===null||e<=r.currentIndex)&&Object.is(r.trackById,t))return r;return null}remove(t){const e=t._prevDup,r=t._nextDup;return e===null?this._head=r:e._nextDup=r,r===null?this._tail=e:r._prevDup=e,this._head===null}}class Gl{map=new Map;put(t){const e=t.trackById;let r=this.map.get(e);r||(r=new sv,this.map.set(e,r)),r.add(t)}get(t,e){const r=t,i=this.map.get(r);return i?i.get(t,e):null}remove(t){const e=t.trackById;return this.map.get(e).remove(t)&&this.map.delete(e),t}get isEmpty(){return this.map.size===0}clear(){this.map.clear()}}function $l(n,t,e){const r=n.previousIndex;if(r===null)return r;let i=0;return e&&r<e.length&&(i=e[r]),r+t+i}class av{supports(t){return t instanceof Map||oa(t)}create(){return new lv}}class lv{_records=new Map;_mapHead=null;_appendAfter=null;_previousMapHead=null;_changesHead=null;_changesTail=null;_additionsHead=null;_additionsTail=null;_removalsHead=null;get isDirty(){return this._additionsHead!==null||this._changesHead!==null||this._removalsHead!==null}forEachItem(t){let e;for(e=this._mapHead;e!==null;e=e._next)t(e)}forEachPreviousItem(t){let e;for(e=this._previousMapHead;e!==null;e=e._nextPrevious)t(e)}forEachChangedItem(t){let e;for(e=this._changesHead;e!==null;e=e._nextChanged)t(e)}forEachAddedItem(t){let e;for(e=this._additionsHead;e!==null;e=e._nextAdded)t(e)}forEachRemovedItem(t){let e;for(e=this._removalsHead;e!==null;e=e._nextRemoved)t(e)}diff(t){if(!t)t=new Map;else if(!(t instanceof Map||oa(t)))throw new V(900,!1);return this.check(t)?this:null}check(t){this._reset();let e=this._mapHead;if(this._appendAfter=null,this._forEach(t,(r,i)=>{if(e&&e.key===i)this._maybeAddToChanges(e,r),this._appendAfter=e,e=e._next;else{const o=this._getOrCreateRecordForKey(i,r);e=this._insertBeforeOrAppend(e,o)}}),e){e._prev&&(e._prev._next=null),this._removalsHead=e;for(let r=e;r!==null;r=r._nextRemoved)r===this._mapHead&&(this._mapHead=null),this._records.delete(r.key),r._nextRemoved=r._next,r.previousValue=r.currentValue,r.currentValue=null,r._prev=null,r._next=null}return this._changesTail&&(this._changesTail._nextChanged=null),this._additionsTail&&(this._additionsTail._nextAdded=null),this.isDirty}_insertBeforeOrAppend(t,e){if(t){const r=t._prev;return e._next=t,e._prev=r,t._prev=e,r&&(r._next=e),t===this._mapHead&&(this._mapHead=e),this._appendAfter=t,t}return this._appendAfter?(this._appendAfter._next=e,e._prev=this._appendAfter):this._mapHead=e,this._appendAfter=e,null}_getOrCreateRecordForKey(t,e){if(this._records.has(t)){const i=this._records.get(t);this._maybeAddToChanges(i,e);const o=i._prev,s=i._next;return o&&(o._next=s),s&&(s._prev=o),i._next=null,i._prev=null,i}const r=new uv(t);return this._records.set(t,r),r.currentValue=e,this._addToAdditions(r),r}_reset(){if(this.isDirty){let t;for(this._previousMapHead=this._mapHead,t=this._previousMapHead;t!==null;t=t._next)t._nextPrevious=t._next;for(t=this._changesHead;t!==null;t=t._nextChanged)t.previousValue=t.currentValue;for(t=this._additionsHead;t!=null;t=t._nextAdded)t.previousValue=t.currentValue;this._changesHead=this._changesTail=null,this._additionsHead=this._additionsTail=null,this._removalsHead=null}}_maybeAddToChanges(t,e){Object.is(e,t.currentValue)||(t.previousValue=t.currentValue,t.currentValue=e,this._addToChanges(t))}_addToAdditions(t){this._additionsHead===null?this._additionsHead=this._additionsTail=t:(this._additionsTail._nextAdded=t,this._additionsTail=t)}_addToChanges(t){this._changesHead===null?this._changesHead=this._changesTail=t:(this._changesTail._nextChanged=t,this._changesTail=t)}_forEach(t,e){t instanceof Map?t.forEach(e):Object.keys(t).forEach(r=>e(t[r],r))}}class uv{key;previousValue=null;currentValue=null;_nextPrevious=null;_next=null;_prev=null;_nextAdded=null;_nextRemoved=null;_nextChanged=null;constructor(t){this.key=t}}function Wl(){return new Fd([new ev])}let Fd=(()=>{class n{factories;static ɵprov=fn({token:n,providedIn:"root",factory:Wl});constructor(e){this.factories=e}static create(e,r){if(r!=null){const i=r.factories.slice();e=e.concat(i)}return new n(e)}static extend(e){return{provide:n,useFactory:()=>{const r=z(n,{optional:!0,skipSelf:!0});return n.create(e,r||Wl())}}}find(e){const r=this.factories.find(i=>i.supports(e));if(r!=null)return r;throw new V(901,!1)}}return n})();function Yl(){return new pa([new av])}let pa=(()=>{class n{static ɵprov=fn({token:n,providedIn:"root",factory:Yl});factories;constructor(e){this.factories=e}static create(e,r){if(r){const i=r.factories.slice();e=e.concat(i)}return new n(e)}static extend(e){return{provide:n,useFactory:()=>{const r=z(n,{optional:!0,skipSelf:!0});return n.create(e,r||Yl())}}}find(e){const r=this.factories.find(i=>i.supports(e));if(r)return r;throw new V(901,!1)}}return n})();function cv(n){const{rootComponent:t,appProviders:e,platformProviders:r,platformRef:i}=n;En(mn.BootstrapApplicationStart);try{const o=i?.injector??Q1(r),s=[V1(),Z0,...e||[]],a=new yd({providers:s,parent:o,debugName:"",runEnvironmentInitializers:!1});return W1({r3Injector:a.injector,platformInjector:o,rootComponent:t})}catch(o){return Promise.reject(o)}finally{En(mn.BootstrapApplicationEnd)}}let Rd=null;function ur(){return Rd}function fv(n){Rd??=n}class dv{}let Od=(()=>{class n{historyGo(e){throw new Error("")}static ɵfac=function(r){return new(r||n)};static ɵprov=fn({token:n,factory:()=>z(hv),providedIn:"platform"})}return n})(),hv=(()=>{class n extends Od{_location;_history;_doc=z(se);constructor(){super(),this._location=window.location,this._history=window.history}getBaseHrefFromDOM(){return ur().getBaseHref(this._doc)}onPopState(e){const r=ur().getGlobalEventTarget(this._doc,"window");return r.addEventListener("popstate",e,!1),()=>r.removeEventListener("popstate",e)}onHashChange(e){const r=ur().getGlobalEventTarget(this._doc,"window");return r.addEventListener("hashchange",e,!1),()=>r.removeEventListener("hashchange",e)}get href(){return this._location.href}get protocol(){return this._location.protocol}get hostname(){return this._location.hostname}get port(){return this._location.port}get pathname(){return this._location.pathname}get search(){return this._location.search}get hash(){return this._location.hash}set pathname(e){this._location.pathname=e}pushState(e,r,i){this._history.pushState(e,r,i)}replaceState(e,r,i){this._history.replaceState(e,r,i)}forward(){this._history.forward()}back(){this._history.back()}historyGo(e=0){this._history.go(e)}getState(){return this._history.state}static ɵfac=function(r){return new(r||n)};static ɵprov=fn({token:n,factory:()=>new n,providedIn:"platform"})}return n})();const Pd={ADP:[void 0,void 0,0],AFN:[void 0,"؋",0],ALL:[void 0,void 0,0],AMD:[void 0,"֏",2],AOA:[void 0,"Kz"],ARS:[void 0,"$"],AUD:["A$","$"],AZN:[void 0,"₼"],BAM:[void 0,"KM"],BBD:[void 0,"$"],BDT:[void 0,"৳"],BHD:[void 0,void 0,3],BIF:[void 0,void 0,0],BMD:[void 0,"$"],BND:[void 0,"$"],BOB:[void 0,"Bs"],BRL:["R$"],BSD:[void 0,"$"],BWP:[void 0,"P"],BYN:[void 0,void 0,2],BYR:[void 0,void 0,0],BZD:[void 0,"$"],CAD:["CA$","$",2],CHF:[void 0,void 0,2],CLF:[void 0,void 0,4],CLP:[void 0,"$",0],CNY:["CN¥","¥"],COP:[void 0,"$",2],CRC:[void 0,"₡",2],CUC:[void 0,"$"],CUP:[void 0,"$"],CZK:[void 0,"Kč",2],DJF:[void 0,void 0,0],DKK:[void 0,"kr",2],DOP:[void 0,"$"],EGP:[void 0,"E£"],ESP:[void 0,"₧",0],EUR:["€"],FJD:[void 0,"$"],FKP:[void 0,"£"],GBP:["£"],GEL:[void 0,"₾"],GHS:[void 0,"GH₵"],GIP:[void 0,"£"],GNF:[void 0,"FG",0],GTQ:[void 0,"Q"],GYD:[void 0,"$",2],HKD:["HK$","$"],HNL:[void 0,"L"],HRK:[void 0,"kn"],HUF:[void 0,"Ft",2],IDR:[void 0,"Rp",2],ILS:["₪"],INR:["₹"],IQD:[void 0,void 0,0],IRR:[void 0,void 0,0],ISK:[void 0,"kr",0],ITL:[void 0,void 0,0],JMD:[void 0,"$"],JOD:[void 0,void 0,3],JPY:["¥",void 0,0],KGS:[void 0,"⃀"],KHR:[void 0,"៛"],KMF:[void 0,"CF",0],KPW:[void 0,"₩",0],KRW:["₩",void 0,0],KWD:[void 0,void 0,3],KYD:[void 0,"$"],KZT:[void 0,"₸"],LAK:[void 0,"₭",0],LBP:[void 0,"L£",0],LKR:[void 0,"Rs"],LRD:[void 0,"$"],LTL:[void 0,"Lt"],LUF:[void 0,void 0,0],LVL:[void 0,"Ls"],LYD:[void 0,void 0,3],MGA:[void 0,"Ar",0],MGF:[void 0,void 0,0],MMK:[void 0,"K",0],MNT:[void 0,"₮",2],MRO:[void 0,void 0,0],MUR:[void 0,"Rs",2],MXN:["MX$","$"],MYR:[void 0,"RM"],NAD:[void 0,"$"],NGN:[void 0,"₦"],NIO:[void 0,"C$"],NOK:[void 0,"kr",2],NPR:[void 0,"Rs"],NZD:["NZ$","$"],OMR:[void 0,void 0,3],PHP:["₱"],PKR:[void 0,"Rs",2],PLN:[void 0,"zł"],PYG:[void 0,"₲",0],RON:[void 0,"lei"],RSD:[void 0,void 0,0],RUB:[void 0,"₽"],RWF:[void 0,"RF",0],SBD:[void 0,"$"],SEK:[void 0,"kr",2],SGD:[void 0,"$"],SHP:[void 0,"£"],SLE:[void 0,void 0,2],SLL:[void 0,void 0,0],SOS:[void 0,void 0,0],SRD:[void 0,"$"],SSP:[void 0,"£"],STD:[void 0,void 0,0],STN:[void 0,"Db"],SYP:[void 0,"£",0],THB:[void 0,"฿"],TMM:[void 0,void 0,0],TND:[void 0,void 0,3],TOP:[void 0,"T$"],TRL:[void 0,void 0,0],TRY:[void 0,"₺"],TTD:[void 0,"$"],TWD:["NT$","$",2],TZS:[void 0,void 0,2],UAH:[void 0,"₴"],UGX:[void 0,void 0,0],USD:["$"],UYI:[void 0,void 0,0],UYU:[void 0,"$"],UYW:[void 0,void 0,4],UZS:[void 0,void 0,2],VEF:[void 0,"Bs",2],VND:["₫",void 0,0],VUV:[void 0,void 0,0],XAF:["FCFA",void 0,0],XCD:["EC$","$"],XCG:["Cg."],XOF:["F CFA",void 0,0],XPF:["CFPF",void 0,0],XXX:["¤"],YER:[void 0,void 0,0],ZAR:[void 0,"R"],ZMK:[void 0,void 0,0],ZMW:[void 0,"ZK"],ZWD:[void 0,void 0,0]};var Ni=(function(n){return n[n.Decimal=0]="Decimal",n[n.Percent=1]="Percent",n[n.Currency=2]="Currency",n[n.Scientific=3]="Scientific",n})(Ni||{}),xe=(function(n){return n[n.Zero=0]="Zero",n[n.One=1]="One",n[n.Two=2]="Two",n[n.Few=3]="Few",n[n.Many=4]="Many",n[n.Other=5]="Other",n})(xe||{}),Xn=(function(n){return n[n.Format=0]="Format",n[n.Standalone=1]="Standalone",n})(Xn||{}),Dn=(function(n){return n[n.Narrow=0]="Narrow",n[n.Abbreviated=1]="Abbreviated",n[n.Wide=2]="Wide",n[n.Short=3]="Short",n})(Dn||{}),rt=(function(n){return n[n.Short=0]="Short",n[n.Medium=1]="Medium",n[n.Long=2]="Long",n[n.Full=3]="Full",n})(rt||{});const Bn={Decimal:0,Group:1,PercentSign:3,MinusSign:5,Exponential:6,Infinity:9,CurrencyDecimal:12,CurrencyGroup:13};function pv(n){return nt(n)[Mn.LocaleId]}function mv(n,t,e){const r=nt(n),i=[r[Mn.DayPeriodsFormat],r[Mn.DayPeriodsStandalone]],o=ct(i,t);return ct(o,e)}function gv(n,t,e){const r=nt(n),i=[r[Mn.DaysFormat],r[Mn.DaysStandalone]],o=ct(i,t);return ct(o,e)}function yv(n,t,e){const r=nt(n),i=[r[Mn.MonthsFormat],r[Mn.MonthsStandalone]],o=ct(i,t);return ct(o,e)}function vv(n,t){const r=nt(n)[Mn.Eras];return ct(r,t)}function Br(n,t){const e=nt(n);return ct(e[Mn.DateFormat],t)}function jr(n,t){const e=nt(n);return ct(e[Mn.TimeFormat],t)}function Hr(n,t){const r=nt(n)[Mn.DateTimeFormat];return ct(r,t)}function lt(n,t){const e=nt(n),r=e[Mn.NumberSymbols][t];if(typeof r>"u"){if(t===Bn.CurrencyDecimal)return e[Mn.NumberSymbols][Bn.Decimal];if(t===Bn.CurrencyGroup)return e[Mn.NumberSymbols][Bn.Group]}return r}function ma(n,t){return nt(n)[Mn.NumberFormats][t]}function wv(n){return nt(n)[Mn.Currencies]}const bv=p1;function Md(n){if(!n[Mn.ExtraData])throw new V(2303,!1)}function _v(n){const t=nt(n);return Md(t),(t[Mn.ExtraData][2]||[]).map(r=>typeof r=="string"?co(r):[co(r[0]),co(r[1])])}function Dv(n,t,e){const r=nt(n);Md(r);const i=[r[Mn.ExtraData][0],r[Mn.ExtraData][1]],o=ct(i,t)||[];return ct(o,e)||[]}function ct(n,t){for(let e=t;e>-1;e--)if(typeof n[e]<"u")return n[e];throw new V(2304,!1)}function co(n){const[t,e]=n.split(":");return{hours:+t,minutes:+e}}function Ev(n,t,e="en"){const r=wv(e)[n]||Pd[n]||[],i=r[1];return t==="narrow"&&typeof i=="string"?i:r[0]||n}const Cv=2;function kv(n){let t;const e=Pd[n];return e&&(t=e[2]),typeof t=="number"?t:Cv}const xv=/^(\d{4,})-?(\d\d)-?(\d\d)(?:T(\d\d)(?::?(\d\d)(?::?(\d\d)(?:\.(\d+))?)?)?(Z|([+-])(\d\d):?(\d\d))?)?$/,Ur={},Tv=/((?:[^BEGHLMOSWYZabcdhmswyz']+)|(?:'(?:[^']|'')*')|(?:G{1,5}|y{1,4}|Y{1,4}|M{1,5}|L{1,5}|w{1,2}|W{1}|d{1,2}|E{1,6}|c{1,6}|a{1,5}|b{1,5}|B{1,5}|h{1,2}|H{1,2}|m{1,2}|s{1,2}|S{1,3}|z{1,4}|Z{1,5}|O{1,4}))([\s\S]*)/;function Iv(n,t,e,r){let i=Nv(n);t=Bt(e,t)||t;let s=[],a;for(;t;)if(a=Tv.exec(t),a){s=s.concat(a.slice(1));const c=s.pop();if(!c)break;t=c}else{s.push(t);break}let l=i.getTimezoneOffset();r&&(l=Nd(r,l),i=Lv(i,r));let u="";return s.forEach(c=>{const f=Pv(c);u+=f?f(i,e,l):c==="''"?"'":c.replace(/(^'|'$)/g,"").replace(/''/g,"'")}),u}function yi(n,t,e){const r=new Date(0);return r.setFullYear(n,t,e),r.setHours(0,0,0),r}function Bt(n,t){const e=pv(n);if(Ur[e]??={},Ur[e][t])return Ur[e][t];let r="";switch(t){case"shortDate":r=Br(n,rt.Short);break;case"mediumDate":r=Br(n,rt.Medium);break;case"longDate":r=Br(n,rt.Long);break;case"fullDate":r=Br(n,rt.Full);break;case"shortTime":r=jr(n,rt.Short);break;case"mediumTime":r=jr(n,rt.Medium);break;case"longTime":r=jr(n,rt.Long);break;case"fullTime":r=jr(n,rt.Full);break;case"short":const i=Bt(n,"shortTime"),o=Bt(n,"shortDate");r=zr(Hr(n,rt.Short),[i,o]);break;case"medium":const s=Bt(n,"mediumTime"),a=Bt(n,"mediumDate");r=zr(Hr(n,rt.Medium),[s,a]);break;case"long":const l=Bt(n,"longTime"),u=Bt(n,"longDate");r=zr(Hr(n,rt.Long),[l,u]);break;case"full":const c=Bt(n,"fullTime"),f=Bt(n,"fullDate");r=zr(Hr(n,rt.Full),[c,f]);break}return r&&(Ur[e][t]=r),r}function zr(n,t){return t&&(n=n.replace(/\{([^}]+)}/g,function(e,r){return t!=null&&r in t?t[r]:e})),n}function vt(n,t,e="-",r,i){let o="";(n<0||i&&n<=0)&&(i?n=-n+1:(n=-n,o=e));let s=String(n);for(;s.length<t;)s="0"+s;return r&&(s=s.slice(s.length-t)),o+s}function Av(n,t){return vt(n,3).substring(0,t)}function Ln(n,t,e=0,r=!1,i=!1){return function(o,s){let a=Sv(n,o);if((e>0||a>-e)&&(a+=e),n===3)a===0&&e===-12&&(a=12);else if(n===6)return Av(a,t);const l=lt(s,Bn.MinusSign);return vt(a,t,l,r,i)}}function Sv(n,t){switch(n){case 0:return t.getFullYear();case 1:return t.getMonth();case 2:return t.getDate();case 3:return t.getHours();case 4:return t.getMinutes();case 5:return t.getSeconds();case 6:return t.getMilliseconds();case 7:return t.getDay();default:throw new V(2301,!1)}}function In(n,t,e=Xn.Format,r=!1){return function(i,o){return Fv(i,o,n,t,e,r)}}function Fv(n,t,e,r,i,o){switch(e){case 2:return yv(t,i,r)[n.getMonth()];case 1:return gv(t,i,r)[n.getDay()];case 0:const s=n.getHours(),a=n.getMinutes();if(o){const l=_v(t),u=Dv(t,i,r),c=l.findIndex(f=>{if(Array.isArray(f)){const[b,p]=f,E=s>=b.hours&&a>=b.minutes,A=s<p.hours||s===p.hours&&a<p.minutes;if(b.hours<p.hours){if(E&&A)return!0}else if(E||A)return!0}else if(f.hours===s&&f.minutes===a)return!0;return!1});if(c!==-1)return u[c]}return mv(t,i,r)[s<12?0:1];case 3:return vv(t,r)[n.getFullYear()<=0?0:1];default:throw new V(2302,!1)}}function Vr(n){return function(t,e,r){const i=-1*r,o=lt(e,Bn.MinusSign),s=i>0?Math.floor(i/60):Math.ceil(i/60);switch(n){case 0:return(i>=0?"+":"")+vt(s,2,o)+vt(Math.abs(i%60),2,o);case 1:return"GMT"+(i>=0?"+":"")+vt(s,1,o);case 2:return"GMT"+(i>=0?"+":"")+vt(s,2,o)+":"+vt(Math.abs(i%60),2,o);case 3:return r===0?"Z":(i>=0?"+":"")+vt(s,2,o)+":"+vt(Math.abs(i%60),2,o);default:throw new V(2310,!1)}}}const Rv=0,Kr=4;function Ov(n){const t=yi(n,Rv,1).getDay();return yi(n,0,1+(t<=Kr?Kr:Kr+7)-t)}function Ld(n){const t=n.getDay(),e=t===0?-3:Kr-t;return yi(n.getFullYear(),n.getMonth(),n.getDate()+e)}function fo(n,t=!1){return function(e,r){let i;if(t){const o=new Date(e.getFullYear(),e.getMonth(),1).getDay()-1,s=e.getDate();i=1+Math.floor((s+o)/7)}else{const o=Ld(e),s=Ov(o.getFullYear()),a=o.getTime()-s.getTime();i=1+Math.round(a/6048e5)}return vt(i,n,lt(r,Bn.MinusSign))}}function Zr(n,t=!1){return function(e,r){const o=Ld(e).getFullYear();return vt(o,n,lt(r,Bn.MinusSign),t)}}const ho={};function Pv(n){if(ho[n])return ho[n];let t;switch(n){case"G":case"GG":case"GGG":t=In(3,Dn.Abbreviated);break;case"GGGG":t=In(3,Dn.Wide);break;case"GGGGG":t=In(3,Dn.Narrow);break;case"y":t=Ln(0,1,0,!1,!0);break;case"yy":t=Ln(0,2,0,!0,!0);break;case"yyy":t=Ln(0,3,0,!1,!0);break;case"yyyy":t=Ln(0,4,0,!1,!0);break;case"Y":t=Zr(1);break;case"YY":t=Zr(2,!0);break;case"YYY":t=Zr(3);break;case"YYYY":t=Zr(4);break;case"M":case"L":t=Ln(1,1,1);break;case"MM":case"LL":t=Ln(1,2,1);break;case"MMM":t=In(2,Dn.Abbreviated);break;case"MMMM":t=In(2,Dn.Wide);break;case"MMMMM":t=In(2,Dn.Narrow);break;case"LLL":t=In(2,Dn.Abbreviated,Xn.Standalone);break;case"LLLL":t=In(2,Dn.Wide,Xn.Standalone);break;case"LLLLL":t=In(2,Dn.Narrow,Xn.Standalone);break;case"w":t=fo(1);break;case"ww":t=fo(2);break;case"W":t=fo(1,!0);break;case"d":t=Ln(2,1);break;case"dd":t=Ln(2,2);break;case"c":case"cc":t=Ln(7,1);break;case"ccc":t=In(1,Dn.Abbreviated,Xn.Standalone);break;case"cccc":t=In(1,Dn.Wide,Xn.Standalone);break;case"ccccc":t=In(1,Dn.Narrow,Xn.Standalone);break;case"cccccc":t=In(1,Dn.Short,Xn.Standalone);break;case"E":case"EE":case"EEE":t=In(1,Dn.Abbreviated);break;case"EEEE":t=In(1,Dn.Wide);break;case"EEEEE":t=In(1,Dn.Narrow);break;case"EEEEEE":t=In(1,Dn.Short);break;case"a":case"aa":case"aaa":t=In(0,Dn.Abbreviated);break;case"aaaa":t=In(0,Dn.Wide);break;case"aaaaa":t=In(0,Dn.Narrow);break;case"b":case"bb":case"bbb":t=In(0,Dn.Abbreviated,Xn.Standalone,!0);break;case"bbbb":t=In(0,Dn.Wide,Xn.Standalone,!0);break;case"bbbbb":t=In(0,Dn.Narrow,Xn.Standalone,!0);break;case"B":case"BB":case"BBB":t=In(0,Dn.Abbreviated,Xn.Format,!0);break;case"BBBB":t=In(0,Dn.Wide,Xn.Format,!0);break;case"BBBBB":t=In(0,Dn.Narrow,Xn.Format,!0);break;case"h":t=Ln(3,1,-12);break;case"hh":t=Ln(3,2,-12);break;case"H":t=Ln(3,1);break;case"HH":t=Ln(3,2);break;case"m":t=Ln(4,1);break;case"mm":t=Ln(4,2);break;case"s":t=Ln(5,1);break;case"ss":t=Ln(5,2);break;case"S":t=Ln(6,1);break;case"SS":t=Ln(6,2);break;case"SSS":t=Ln(6,3);break;case"Z":case"ZZ":case"ZZZ":t=Vr(0);break;case"ZZZZZ":t=Vr(3);break;case"O":case"OO":case"OOO":case"z":case"zz":case"zzz":t=Vr(1);break;case"OOOO":case"ZZZZ":case"zzzz":t=Vr(2);break;default:return null}return ho[n]=t,t}function Nd(n,t){n=n.replace(/:/g,"");const e=Date.parse("Jan 01, 1970 00:00:00 "+n)/6e4;return isNaN(e)?t:e}function Mv(n,t){return n=new Date(n.getTime()),n.setMinutes(n.getMinutes()+t),n}function Lv(n,t,e){const i=n.getTimezoneOffset(),o=Nd(t,i);return Mv(n,-1*(o-i))}function Nv(n){if(Xl(n))return n;if(typeof n=="number"&&!isNaN(n))return new Date(n);if(typeof n=="string"){if(n=n.trim(),/^(\d{4}(-\d{1,2}(-\d{1,2})?)?)$/.test(n)){const[i,o=1,s=1]=n.split("-").map(a=>+a);return yi(i,o-1,s)}const e=parseFloat(n);if(!isNaN(n-e))return new Date(e);let r;if(r=n.match(xv))return Bv(r)}const t=new Date(n);if(!Xl(t))throw new V(2311,!1);return t}function Bv(n){const t=new Date(0);let e=0,r=0;const i=n[8]?t.setUTCFullYear:t.setFullYear,o=n[8]?t.setUTCHours:t.setHours;n[9]&&(e=Number(n[9]+n[10]),r=Number(n[9]+n[11])),i.call(t,Number(n[1]),Number(n[2])-1,Number(n[3]));const s=Number(n[4]||0)-e,a=Number(n[5]||0)-r,l=Number(n[6]||0),u=Math.floor(parseFloat("0."+(n[7]||0))*1e3);return o.call(t,s,a,l,u),t}function Xl(n){return n instanceof Date&&!isNaN(n.valueOf())}const jv=/^(\d+)?\.((\d+)(-(\d+))?)?$/,ql=22,vi=".",cr="0",Hv=";",Uv=",",po="#",Ql="¤",zv="%";function ga(n,t,e,r,i,o,s=!1){let a="",l=!1;if(!isFinite(n))a=lt(e,Bn.Infinity);else{let u=Wv(n);s&&(u=$v(u));let c=t.minInt,f=t.minFrac,b=t.maxFrac;if(o){const gn=o.match(jv);if(gn===null)throw new V(2306,!1);const tt=gn[1],kn=gn[3],ot=gn[5];tt!=null&&(c=mo(tt)),kn!=null&&(f=mo(kn)),ot!=null?b=mo(ot):kn!=null&&f>b&&(b=f)}Yv(u,f,b);let p=u.digits,E=u.integerLen;const A=u.exponent;let F=[];for(l=p.every(gn=>!gn);E<c;E++)p.unshift(0);for(;E<0;E++)p.unshift(0);E>0?F=p.splice(E,p.length):(F=p,p=[0]);const S=[];for(p.length>=t.lgSize&&S.unshift(p.splice(-t.lgSize,p.length).join(""));p.length>t.gSize;)S.unshift(p.splice(-t.gSize,p.length).join(""));p.length&&S.unshift(p.join("")),a=S.join(lt(e,r)),F.length&&(a+=lt(e,i)+F.join("")),A&&(a+=lt(e,Bn.Exponential)+"+"+A)}return n<0&&!l?a=t.negPre+a+t.negSuf:a=t.posPre+a+t.posSuf,a}function Vv(n,t,e,r,i){const o=ma(t,Ni.Currency),s=ya(o,lt(t,Bn.MinusSign));return s.minFrac=kv(r),s.maxFrac=s.minFrac,ga(n,s,t,Bn.CurrencyGroup,Bn.CurrencyDecimal,i).replace(Ql,e).replace(Ql,"").trim()}function Zv(n,t,e){const r=ma(t,Ni.Percent),i=ya(r,lt(t,Bn.MinusSign));return ga(n,i,t,Bn.Group,Bn.Decimal,e,!0).replace(new RegExp(zv,"g"),lt(t,Bn.PercentSign))}function Gv(n,t,e){const r=ma(t,Ni.Decimal),i=ya(r,lt(t,Bn.MinusSign));return ga(n,i,t,Bn.Group,Bn.Decimal,e)}function ya(n,t="-"){const e={minInt:1,minFrac:0,maxFrac:0,posPre:"",posSuf:"",negPre:"",negSuf:"",gSize:0,lgSize:0},r=n.split(Hv),i=r[0],o=r[1],s=i.indexOf(vi)!==-1?i.split(vi):[i.substring(0,i.lastIndexOf(cr)+1),i.substring(i.lastIndexOf(cr)+1)],a=s[0],l=s[1]||"";e.posPre=a.substring(0,a.indexOf(po));for(let c=0;c<l.length;c++){const f=l.charAt(c);f===cr?e.minFrac=e.maxFrac=c+1:f===po?e.maxFrac=c+1:e.posSuf+=f}const u=a.split(Uv);if(e.gSize=u[1]?u[1].length:0,e.lgSize=u[2]||u[1]?(u[2]||u[1]).length:0,o){const c=i.length-e.posPre.length-e.posSuf.length,f=o.indexOf(po);e.negPre=o.substring(0,f).replace(/'/g,""),e.negSuf=o.slice(f+c).replace(/'/g,"")}else e.negPre=t+e.posPre,e.negSuf=e.posSuf;return e}function $v(n){if(n.digits[0]===0)return n;const t=n.digits.length-n.integerLen;return n.exponent?n.exponent+=2:(t===0?n.digits.push(0,0):t===1&&n.digits.push(0),n.integerLen+=2),n}function Wv(n){let t=Math.abs(n)+"",e=0,r,i,o,s,a;for((i=t.indexOf(vi))>-1&&(t=t.replace(vi,"")),(o=t.search(/e/i))>0?(i<0&&(i=o),i+=+t.slice(o+1),t=t.substring(0,o)):i<0&&(i=t.length),o=0;t.charAt(o)===cr;o++);if(o===(a=t.length))r=[0],i=1;else{for(a--;t.charAt(a)===cr;)a--;for(i-=o,r=[],s=0;o<=a;o++,s++)r[s]=Number(t.charAt(o))}return i>ql&&(r=r.splice(0,ql-1),e=i-1,i=1),{digits:r,exponent:e,integerLen:i}}function Yv(n,t,e){if(t>e)throw new V(2307,!1);let r=n.digits,i=r.length-n.integerLen;const o=Math.min(Math.max(t,i),e);let s=o+n.integerLen,a=r[s];if(s>0){r.splice(Math.max(n.integerLen,s));for(let f=s;f<r.length;f++)r[f]=0}else{i=Math.max(0,i),n.integerLen=1,r.length=Math.max(1,s=o+1),r[0]=0;for(let f=1;f<s;f++)r[f]=0}if(a>=5)if(s-1<0){for(let f=0;f>s;f--)r.unshift(0),n.integerLen++;r.unshift(1),n.integerLen++}else r[s-1]++;for(;i<Math.max(0,o);i++)r.push(0);let l=o!==0;const u=t+n.integerLen,c=r.reduceRight(function(f,b,p,E){return b=b+f,E[p]=b<10?b:b-10,l&&(E[p]===0&&p>=u?E.pop():l=!1),b>=10?1:0},0);c&&(r.unshift(c),n.integerLen++)}function mo(n){const t=parseInt(n);if(isNaN(t))throw new V(2305,!1);return t}let va=(()=>{class n{static ɵfac=function(r){return new(r||n)};static ɵprov=fn({token:n,factory:()=>new Xv(z(ue)),providedIn:"root"})}return n})();function Bd(n,t,e,r){let i=`=${n}`;if(t.indexOf(i)>-1||(i=e.getPluralCategory(n,r),t.indexOf(i)>-1))return i;if(t.indexOf("other")>-1)return"other";throw new V(2308,!1)}let Xv=(()=>{class n extends va{locale;constructor(e){super(),this.locale=e}getPluralCategory(e,r){switch(bv(r||this.locale)(e)){case xe.Zero:return"zero";case xe.One:return"one";case xe.Two:return"two";case xe.Few:return"few";case xe.Many:return"many";default:return"other"}}static ɵfac=function(r){return new(r||n)(cn(ue))};static ɵprov=fn({token:n,factory:n.ɵfac})}return n})();const go=/\s+/,Jl=[];let Kl=(()=>{class n{_ngEl;_renderer;initialClasses=Jl;rawClass;stateMap=new Map;constructor(e,r){this._ngEl=e,this._renderer=r}set klass(e){this.initialClasses=e!=null?e.trim().split(go):Jl}set ngClass(e){this.rawClass=typeof e=="string"?e.trim().split(go):e}ngDoCheck(){for(const r of this.initialClasses)this._updateState(r,!0);const e=this.rawClass;if(Array.isArray(e)||e instanceof Set)for(const r of e)this._updateState(r,!0);else if(e!=null)for(const r of Object.keys(e))this._updateState(r,!!e[r]);this._applyStateDiff()}_updateState(e,r){const i=this.stateMap.get(e);i!==void 0?(i.enabled!==r&&(i.changed=!0,i.enabled=r),i.touched=!0):this.stateMap.set(e,{enabled:r,changed:!0,touched:!0})}_applyStateDiff(){for(const e of this.stateMap){const r=e[0],i=e[1];i.changed?(this._toggleClass(r,i.enabled),i.changed=!1):i.touched||(i.enabled&&this._toggleClass(r,!1),this.stateMap.delete(r)),i.touched=!1}}_toggleClass(e,r){e=e.trim(),e.length>0&&e.split(go).forEach(i=>{r?this._renderer.addClass(this._ngEl.nativeElement,i):this._renderer.removeClass(this._ngEl.nativeElement,i)})}static ɵfac=function(r){return new(r||n)(an(Gs),an(ld))};static ɵdir=kt({type:n,selectors:[["","ngClass",""]],inputs:{klass:[0,"class","klass"],ngClass:"ngClass"}})}return n})(),nu=(()=>{class n{_viewContainerRef;ngComponentOutlet=null;ngComponentOutletInputs;ngComponentOutletInjector;ngComponentOutletEnvironmentInjector;ngComponentOutletContent;ngComponentOutletNgModule;_componentRef;_moduleRef;_inputsUsed=new Map;get componentInstance(){return this._componentRef?.instance??null}constructor(e){this._viewContainerRef=e}_needToReCreateNgModuleInstance(e){return e.ngComponentOutletNgModule!==void 0}_needToReCreateComponentInstance(e){return e.ngComponentOutlet!==void 0||e.ngComponentOutletContent!==void 0||e.ngComponentOutletInjector!==void 0||e.ngComponentOutletEnvironmentInjector!==void 0||this._needToReCreateNgModuleInstance(e)}ngOnChanges(e){if(this._needToReCreateComponentInstance(e)&&(this._viewContainerRef.clear(),this._inputsUsed.clear(),this._componentRef=void 0,this.ngComponentOutlet)){const r=this.ngComponentOutletInjector||this._viewContainerRef.parentInjector;this._needToReCreateNgModuleInstance(e)&&(this._moduleRef?.destroy(),this.ngComponentOutletNgModule?this._moduleRef=By(this.ngComponentOutletNgModule,qv(r)):this._moduleRef=void 0),this._componentRef=this._viewContainerRef.createComponent(this.ngComponentOutlet,{injector:r,ngModuleRef:this._moduleRef,projectableNodes:this.ngComponentOutletContent,environmentInjector:this.ngComponentOutletEnvironmentInjector})}}ngDoCheck(){if(this._componentRef){if(this.ngComponentOutletInputs)for(const e of Object.keys(this.ngComponentOutletInputs))this._inputsUsed.set(e,!0);this._applyInputStateDiff(this._componentRef)}}ngOnDestroy(){this._moduleRef?.destroy()}_applyInputStateDiff(e){for(const[r,i]of this._inputsUsed)i?(e.setInput(r,this.ngComponentOutletInputs[r]),this._inputsUsed.set(r,!1)):(e.setInput(r,void 0),this._inputsUsed.delete(r))}static ɵfac=function(r){return new(r||n)(an(le))};static ɵdir=kt({type:n,selectors:[["","ngComponentOutlet",""]],inputs:{ngComponentOutlet:"ngComponentOutlet",ngComponentOutletInputs:"ngComponentOutletInputs",ngComponentOutletInjector:"ngComponentOutletInjector",ngComponentOutletEnvironmentInjector:"ngComponentOutletEnvironmentInjector",ngComponentOutletContent:"ngComponentOutletContent",ngComponentOutletNgModule:"ngComponentOutletNgModule"},exportAs:["ngComponentOutlet"],features:[nf]})}return n})();function qv(n){return n.get(Ve).injector}class Qv{$implicit;ngForOf;index;count;constructor(t,e,r,i){this.$implicit=t,this.ngForOf=e,this.index=r,this.count=i}get first(){return this.index===0}get last(){return this.index===this.count-1}get even(){return this.index%2===0}get odd(){return!this.even}}let tu=(()=>{class n{_viewContainer;_template;_differs;set ngForOf(e){this._ngForOf=e,this._ngForOfDirty=!0}set ngForTrackBy(e){this._trackByFn=e}get ngForTrackBy(){return this._trackByFn}_ngForOf=null;_ngForOfDirty=!0;_differ=null;_trackByFn;constructor(e,r,i){this._viewContainer=e,this._template=r,this._differs=i}set ngForTemplate(e){e&&(this._template=e)}ngDoCheck(){if(this._ngForOfDirty){this._ngForOfDirty=!1;const e=this._ngForOf;!this._differ&&e&&(this._differ=this._differs.find(e).create(this.ngForTrackBy))}if(this._differ){const e=this._differ.diff(this._ngForOf);e&&this._applyChanges(e)}}_applyChanges(e){const r=this._viewContainer;e.forEachOperation((i,o,s)=>{if(i.previousIndex==null)r.createEmbeddedView(this._template,new Qv(i.item,this._ngForOf,-1,-1),s===null?void 0:s);else if(s==null)r.remove(o===null?void 0:o);else if(o!==null){const a=r.get(o);r.move(a,s),eu(a,i)}});for(let i=0,o=r.length;i<o;i++){const a=r.get(i).context;a.index=i,a.count=o,a.ngForOf=this._ngForOf}e.forEachIdentityChange(i=>{const o=r.get(i.currentIndex);eu(o,i)})}static ngTemplateContextGuard(e,r){return!0}static ɵfac=function(r){return new(r||n)(an(le),an(Je),an(Fd))};static ɵdir=kt({type:n,selectors:[["","ngFor","","ngForOf",""]],inputs:{ngForOf:"ngForOf",ngForTrackBy:"ngForTrackBy",ngForTemplate:"ngForTemplate"}})}return n})();function eu(n,t){n.context.$implicit=t.item}let ru=(()=>{class n{_viewContainer;_context=new Jv;_thenTemplateRef=null;_elseTemplateRef=null;_thenViewRef=null;_elseViewRef=null;constructor(e,r){this._viewContainer=e,this._thenTemplateRef=r}set ngIf(e){this._context.$implicit=this._context.ngIf=e,this._updateView()}set ngIfThen(e){iu(e),this._thenTemplateRef=e,this._thenViewRef=null,this._updateView()}set ngIfElse(e){iu(e),this._elseTemplateRef=e,this._elseViewRef=null,this._updateView()}_updateView(){this._context.$implicit?this._thenViewRef||(this._viewContainer.clear(),this._elseViewRef=null,this._thenTemplateRef&&(this._thenViewRef=this._viewContainer.createEmbeddedView(this._thenTemplateRef,this._context))):this._elseViewRef||(this._viewContainer.clear(),this._thenViewRef=null,this._elseTemplateRef&&(this._elseViewRef=this._viewContainer.createEmbeddedView(this._elseTemplateRef,this._context)))}static ngIfUseIfTypeGuard;static ngTemplateGuard_ngIf;static ngTemplateContextGuard(e,r){return!0}static ɵfac=function(r){return new(r||n)(an(le),an(Je))};static ɵdir=kt({type:n,selectors:[["","ngIf",""]],inputs:{ngIf:"ngIf",ngIfThen:"ngIfThen",ngIfElse:"ngIfElse"}})}return n})();class Jv{$implicit=null;ngIf=null}function iu(n,t){if(n&&!n.createEmbeddedView)throw new V(2020,!1)}class wa{_viewContainerRef;_templateRef;_created=!1;constructor(t,e){this._viewContainerRef=t,this._templateRef=e}create(){this._created=!0,this._viewContainerRef.createEmbeddedView(this._templateRef)}destroy(){this._created=!1,this._viewContainerRef.clear()}enforceState(t){t&&!this._created?this.create():!t&&this._created&&this.destroy()}}let wi=(()=>{class n{_defaultViews=[];_defaultUsed=!1;_caseCount=0;_lastCaseCheckIndex=0;_lastCasesMatched=!1;_ngSwitch;set ngSwitch(e){this._ngSwitch=e,this._caseCount===0&&this._updateDefaultCases(!0)}_addCase(){return this._caseCount++}_addDefault(e){this._defaultViews.push(e)}_matchCase(e){const r=e===this._ngSwitch;return this._lastCasesMatched||=r,this._lastCaseCheckIndex++,this._lastCaseCheckIndex===this._caseCount&&(this._updateDefaultCases(!this._lastCasesMatched),this._lastCaseCheckIndex=0,this._lastCasesMatched=!1),r}_updateDefaultCases(e){if(this._defaultViews.length>0&&e!==this._defaultUsed){this._defaultUsed=e;for(const r of this._defaultViews)r.enforceState(e)}}static ɵfac=function(r){return new(r||n)};static ɵdir=kt({type:n,selectors:[["","ngSwitch",""]],inputs:{ngSwitch:"ngSwitch"}})}return n})(),ou=(()=>{class n{ngSwitch;_view;ngSwitchCase;constructor(e,r,i){this.ngSwitch=i,i._addCase(),this._view=new wa(e,r)}ngDoCheck(){this._view.enforceState(this.ngSwitch._matchCase(this.ngSwitchCase))}static ɵfac=function(r){return new(r||n)(an(le),an(Je),an(wi,9))};static ɵdir=kt({type:n,selectors:[["","ngSwitchCase",""]],inputs:{ngSwitchCase:"ngSwitchCase"}})}return n})(),su=(()=>{class n{constructor(e,r,i){i._addDefault(new wa(e,r))}static ɵfac=function(r){return new(r||n)(an(le),an(Je),an(wi,9))};static ɵdir=kt({type:n,selectors:[["","ngSwitchDefault",""]]})}return n})(),os=(()=>{class n{_localization;_activeView;_caseViews={};constructor(e){this._localization=e}set ngPlural(e){this._updateView(e)}addCase(e,r){this._caseViews[e]=r}_updateView(e){this._clearViews();const r=Object.keys(this._caseViews),i=Bd(e,r,this._localization);this._activateView(this._caseViews[i])}_clearViews(){this._activeView&&this._activeView.destroy()}_activateView(e){e&&(this._activeView=e,this._activeView.create())}static ɵfac=function(r){return new(r||n)(an(va))};static ɵdir=kt({type:n,selectors:[["","ngPlural",""]],inputs:{ngPlural:"ngPlural"}})}return n})(),au=(()=>{class n{value;constructor(e,r,i,o){this.value=e;const s=!isNaN(Number(e));o.addCase(s?`=${e}`:e,new wa(i,r))}static ɵfac=function(r){return new(r||n)(vm("ngPluralCase"),an(Je),an(le),an(os,1))};static ɵdir=kt({type:n,selectors:[["","ngPluralCase",""]]})}return n})(),lu=(()=>{class n{_ngEl;_differs;_renderer;_ngStyle=null;_differ=null;constructor(e,r,i){this._ngEl=e,this._differs=r,this._renderer=i}set ngStyle(e){this._ngStyle=e,!this._differ&&e&&(this._differ=this._differs.find(e).create())}ngDoCheck(){if(this._differ){const e=this._differ.diff(this._ngStyle);e&&this._applyChanges(e)}}_setStyle(e,r){const[i,o]=e.split("."),s=i.indexOf("-")===-1?void 0:Kt.DashCase;r!=null?this._renderer.setStyle(this._ngEl.nativeElement,i,o?`${r}${o}`:r,s):this._renderer.removeStyle(this._ngEl.nativeElement,i,s)}_applyChanges(e){e.forEachRemovedItem(r=>this._setStyle(r.key,null)),e.forEachAddedItem(r=>this._setStyle(r.key,r.currentValue)),e.forEachChangedItem(r=>this._setStyle(r.key,r.currentValue))}static ɵfac=function(r){return new(r||n)(an(Gs),an(pa),an(ld))};static ɵdir=kt({type:n,selectors:[["","ngStyle",""]],inputs:{ngStyle:"ngStyle"}})}return n})(),uu=(()=>{class n{_viewContainerRef;_viewRef=null;ngTemplateOutletContext=null;ngTemplateOutlet=null;ngTemplateOutletInjector=null;injector=z(qe);constructor(e){this._viewContainerRef=e}ngOnChanges(e){if(this._shouldRecreateView(e)){const r=this._viewContainerRef;if(this._viewRef&&r.remove(r.indexOf(this._viewRef)),!this.ngTemplateOutlet){this._viewRef=null;return}const i=this._createContextForwardProxy();this._viewRef=r.createEmbeddedView(this.ngTemplateOutlet,i,{injector:this._getInjector()})}}_getInjector(){return this.ngTemplateOutletInjector==="outlet"?this.injector:this.ngTemplateOutletInjector??void 0}_shouldRecreateView(e){return!!e.ngTemplateOutlet||!!e.ngTemplateOutletInjector}_createContextForwardProxy(){return new Proxy({},{set:(e,r,i)=>this.ngTemplateOutletContext?Reflect.set(this.ngTemplateOutletContext,r,i):!1,get:(e,r,i)=>{if(this.ngTemplateOutletContext)return Reflect.get(this.ngTemplateOutletContext,r,i)}})}static ɵfac=function(r){return new(r||n)(an(le))};static ɵdir=kt({type:n,selectors:[["","ngTemplateOutlet",""]],inputs:{ngTemplateOutletContext:"ngTemplateOutletContext",ngTemplateOutlet:"ngTemplateOutlet",ngTemplateOutletInjector:"ngTemplateOutletInjector"},features:[nf]})}return n})();function Xt(n,t){return new V(2100,!1)}class Kv{createSubscription(t,e,r){return Zl(()=>t.subscribe({next:e,error:r}))}dispose(t){Zl(()=>t.unsubscribe())}}class nw{createSubscription(t,e,r){return t.then(i=>e?.(i),i=>r?.(i)),{unsubscribe:()=>{e=null,r=null}}}dispose(t){t.unsubscribe()}}const tw=new nw,ew=new Kv;let cu=(()=>{class n{_ref;_latestValue=null;markForCheckOnValueUpdate=!0;_subscription=null;_obj=null;_strategy=null;applicationErrorHandler=z(Qe);constructor(e){this._ref=e}ngOnDestroy(){this._subscription&&this._dispose(),this._ref=null}transform(e){if(!this._obj){if(e)try{this.markForCheckOnValueUpdate=!1,this._subscribe(e)}finally{this.markForCheckOnValueUpdate=!0}return this._latestValue}return e!==this._obj?(this._dispose(),this.transform(e)):this._latestValue}_subscribe(e){this._obj=e,this._strategy=this._selectStrategy(e),this._subscription=this._strategy.createSubscription(e,r=>this._updateLatestValue(e,r),r=>this.applicationErrorHandler(r))}_selectStrategy(e){if(aa(e))return tw;if(_d(e))return ew;throw Xt()}_dispose(){this._strategy.dispose(this._subscription),this._latestValue=null,this._subscription=null,this._obj=null}_updateLatestValue(e,r){e===this._obj&&(this._latestValue=r,this.markForCheckOnValueUpdate&&this._ref?.markForCheck())}static ɵfac=function(r){return new(r||n)(an(K1,16))};static ɵpipe=it({name:"async",type:n,pure:!1})}return n})(),fu=(()=>{class n{transform(e){return e==null?null:(ba(n,e),e.toLowerCase())}static ɵfac=function(r){return new(r||n)};static ɵpipe=it({name:"lowercase",type:n,pure:!0})}return n})();const rw=/(?:[0-9A-Za-z\xAA\xB5\xBA\xC0-\xD6\xD8-\xF6\xF8-\u02C1\u02C6-\u02D1\u02E0-\u02E4\u02EC\u02EE\u0370-\u0374\u0376\u0377\u037A-\u037D\u037F\u0386\u0388-\u038A\u038C\u038E-\u03A1\u03A3-\u03F5\u03F7-\u0481\u048A-\u052F\u0531-\u0556\u0559\u0560-\u0588\u05D0-\u05EA\u05EF-\u05F2\u0620-\u064A\u066E\u066F\u0671-\u06D3\u06D5\u06E5\u06E6\u06EE\u06EF\u06FA-\u06FC\u06FF\u0710\u0712-\u072F\u074D-\u07A5\u07B1\u07CA-\u07EA\u07F4\u07F5\u07FA\u0800-\u0815\u081A\u0824\u0828\u0840-\u0858\u0860-\u086A\u0870-\u0887\u0889-\u088E\u08A0-\u08C9\u0904-\u0939\u093D\u0950\u0958-\u0961\u0971-\u0980\u0985-\u098C\u098F\u0990\u0993-\u09A8\u09AA-\u09B0\u09B2\u09B6-\u09B9\u09BD\u09CE\u09DC\u09DD\u09DF-\u09E1\u09F0\u09F1\u09FC\u0A05-\u0A0A\u0A0F\u0A10\u0A13-\u0A28\u0A2A-\u0A30\u0A32\u0A33\u0A35\u0A36\u0A38\u0A39\u0A59-\u0A5C\u0A5E\u0A72-\u0A74\u0A85-\u0A8D\u0A8F-\u0A91\u0A93-\u0AA8\u0AAA-\u0AB0\u0AB2\u0AB3\u0AB5-\u0AB9\u0ABD\u0AD0\u0AE0\u0AE1\u0AF9\u0B05-\u0B0C\u0B0F\u0B10\u0B13-\u0B28\u0B2A-\u0B30\u0B32\u0B33\u0B35-\u0B39\u0B3D\u0B5C\u0B5D\u0B5F-\u0B61\u0B71\u0B83\u0B85-\u0B8A\u0B8E-\u0B90\u0B92-\u0B95\u0B99\u0B9A\u0B9C\u0B9E\u0B9F\u0BA3\u0BA4\u0BA8-\u0BAA\u0BAE-\u0BB9\u0BD0\u0C05-\u0C0C\u0C0E-\u0C10\u0C12-\u0C28\u0C2A-\u0C39\u0C3D\u0C58-\u0C5A\u0C5D\u0C60\u0C61\u0C80\u0C85-\u0C8C\u0C8E-\u0C90\u0C92-\u0CA8\u0CAA-\u0CB3\u0CB5-\u0CB9\u0CBD\u0CDD\u0CDE\u0CE0\u0CE1\u0CF1\u0CF2\u0D04-\u0D0C\u0D0E-\u0D10\u0D12-\u0D3A\u0D3D\u0D4E\u0D54-\u0D56\u0D5F-\u0D61\u0D7A-\u0D7F\u0D85-\u0D96\u0D9A-\u0DB1\u0DB3-\u0DBB\u0DBD\u0DC0-\u0DC6\u0E01-\u0E30\u0E32\u0E33\u0E40-\u0E46\u0E81\u0E82\u0E84\u0E86-\u0E8A\u0E8C-\u0EA3\u0EA5\u0EA7-\u0EB0\u0EB2\u0EB3\u0EBD\u0EC0-\u0EC4\u0EC6\u0EDC-\u0EDF\u0F00\u0F40-\u0F47\u0F49-\u0F6C\u0F88-\u0F8C\u1000-\u102A\u103F\u1050-\u1055\u105A-\u105D\u1061\u1065\u1066\u106E-\u1070\u1075-\u1081\u108E\u10A0-\u10C5\u10C7\u10CD\u10D0-\u10FA\u10FC-\u1248\u124A-\u124D\u1250-\u1256\u1258\u125A-\u125D\u1260-\u1288\u128A-\u128D\u1290-\u12B0\u12B2-\u12B5\u12B8-\u12BE\u12C0\u12C2-\u12C5\u12C8-\u12D6\u12D8-\u1310\u1312-\u1315\u1318-\u135A\u1380-\u138F\u13A0-\u13F5\u13F8-\u13FD\u1401-\u166C\u166F-\u167F\u1681-\u169A\u16A0-\u16EA\u16F1-\u16F8\u1700-\u1711\u171F-\u1731\u1740-\u1751\u1760-\u176C\u176E-\u1770\u1780-\u17B3\u17D7\u17DC\u1820-\u1878\u1880-\u1884\u1887-\u18A8\u18AA\u18B0-\u18F5\u1900-\u191E\u1950-\u196D\u1970-\u1974\u1980-\u19AB\u19B0-\u19C9\u1A00-\u1A16\u1A20-\u1A54\u1AA7\u1B05-\u1B33\u1B45-\u1B4C\u1B83-\u1BA0\u1BAE\u1BAF\u1BBA-\u1BE5\u1C00-\u1C23\u1C4D-\u1C4F\u1C5A-\u1C7D\u1C80-\u1C88\u1C90-\u1CBA\u1CBD-\u1CBF\u1CE9-\u1CEC\u1CEE-\u1CF3\u1CF5\u1CF6\u1CFA\u1D00-\u1DBF\u1E00-\u1F15\u1F18-\u1F1D\u1F20-\u1F45\u1F48-\u1F4D\u1F50-\u1F57\u1F59\u1F5B\u1F5D\u1F5F-\u1F7D\u1F80-\u1FB4\u1FB6-\u1FBC\u1FBE\u1FC2-\u1FC4\u1FC6-\u1FCC\u1FD0-\u1FD3\u1FD6-\u1FDB\u1FE0-\u1FEC\u1FF2-\u1FF4\u1FF6-\u1FFC\u2071\u207F\u2090-\u209C\u2102\u2107\u210A-\u2113\u2115\u2119-\u211D\u2124\u2126\u2128\u212A-\u212D\u212F-\u2139\u213C-\u213F\u2145-\u2149\u214E\u2183\u2184\u2C00-\u2CE4\u2CEB-\u2CEE\u2CF2\u2CF3\u2D00-\u2D25\u2D27\u2D2D\u2D30-\u2D67\u2D6F\u2D80-\u2D96\u2DA0-\u2DA6\u2DA8-\u2DAE\u2DB0-\u2DB6\u2DB8-\u2DBE\u2DC0-\u2DC6\u2DC8-\u2DCE\u2DD0-\u2DD6\u2DD8-\u2DDE\u2E2F\u3005\u3006\u3031-\u3035\u303B\u303C\u3041-\u3096\u309D-\u309F\u30A1-\u30FA\u30FC-\u30FF\u3105-\u312F\u3131-\u318E\u31A0-\u31BF\u31F0-\u31FF\u3400-\u4DBF\u4E00-\uA48C\uA4D0-\uA4FD\uA500-\uA60C\uA610-\uA61F\uA62A\uA62B\uA640-\uA66E\uA67F-\uA69D\uA6A0-\uA6E5\uA717-\uA71F\uA722-\uA788\uA78B-\uA7CA\uA7D0\uA7D1\uA7D3\uA7D5-\uA7D9\uA7F2-\uA801\uA803-\uA805\uA807-\uA80A\uA80C-\uA822\uA840-\uA873\uA882-\uA8B3\uA8F2-\uA8F7\uA8FB\uA8FD\uA8FE\uA90A-\uA925\uA930-\uA946\uA960-\uA97C\uA984-\uA9B2\uA9CF\uA9E0-\uA9E4\uA9E6-\uA9EF\uA9FA-\uA9FE\uAA00-\uAA28\uAA40-\uAA42\uAA44-\uAA4B\uAA60-\uAA76\uAA7A\uAA7E-\uAAAF\uAAB1\uAAB5\uAAB6\uAAB9-\uAABD\uAAC0\uAAC2\uAADB-\uAADD\uAAE0-\uAAEA\uAAF2-\uAAF4\uAB01-\uAB06\uAB09-\uAB0E\uAB11-\uAB16\uAB20-\uAB26\uAB28-\uAB2E\uAB30-\uAB5A\uAB5C-\uAB69\uAB70-\uABE2\uAC00-\uD7A3\uD7B0-\uD7C6\uD7CB-\uD7FB\uF900-\uFA6D\uFA70-\uFAD9\uFB00-\uFB06\uFB13-\uFB17\uFB1D\uFB1F-\uFB28\uFB2A-\uFB36\uFB38-\uFB3C\uFB3E\uFB40\uFB41\uFB43\uFB44\uFB46-\uFBB1\uFBD3-\uFD3D\uFD50-\uFD8F\uFD92-\uFDC7\uFDF0-\uFDFB\uFE70-\uFE74\uFE76-\uFEFC\uFF21-\uFF3A\uFF41-\uFF5A\uFF66-\uFFBE\uFFC2-\uFFC7\uFFCA-\uFFCF\uFFD2-\uFFD7\uFFDA-\uFFDC]|\uD800[\uDC00-\uDC0B\uDC0D-\uDC26\uDC28-\uDC3A\uDC3C\uDC3D\uDC3F-\uDC4D\uDC50-\uDC5D\uDC80-\uDCFA\uDE80-\uDE9C\uDEA0-\uDED0\uDF00-\uDF1F\uDF2D-\uDF40\uDF42-\uDF49\uDF50-\uDF75\uDF80-\uDF9D\uDFA0-\uDFC3\uDFC8-\uDFCF]|\uD801[\uDC00-\uDC9D\uDCB0-\uDCD3\uDCD8-\uDCFB\uDD00-\uDD27\uDD30-\uDD63\uDD70-\uDD7A\uDD7C-\uDD8A\uDD8C-\uDD92\uDD94\uDD95\uDD97-\uDDA1\uDDA3-\uDDB1\uDDB3-\uDDB9\uDDBB\uDDBC\uDE00-\uDF36\uDF40-\uDF55\uDF60-\uDF67\uDF80-\uDF85\uDF87-\uDFB0\uDFB2-\uDFBA]|\uD802[\uDC00-\uDC05\uDC08\uDC0A-\uDC35\uDC37\uDC38\uDC3C\uDC3F-\uDC55\uDC60-\uDC76\uDC80-\uDC9E\uDCE0-\uDCF2\uDCF4\uDCF5\uDD00-\uDD15\uDD20-\uDD39\uDD80-\uDDB7\uDDBE\uDDBF\uDE00\uDE10-\uDE13\uDE15-\uDE17\uDE19-\uDE35\uDE60-\uDE7C\uDE80-\uDE9C\uDEC0-\uDEC7\uDEC9-\uDEE4\uDF00-\uDF35\uDF40-\uDF55\uDF60-\uDF72\uDF80-\uDF91]|\uD803[\uDC00-\uDC48\uDC80-\uDCB2\uDCC0-\uDCF2\uDD00-\uDD23\uDE80-\uDEA9\uDEB0\uDEB1\uDF00-\uDF1C\uDF27\uDF30-\uDF45\uDF70-\uDF81\uDFB0-\uDFC4\uDFE0-\uDFF6]|\uD804[\uDC03-\uDC37\uDC71\uDC72\uDC75\uDC83-\uDCAF\uDCD0-\uDCE8\uDD03-\uDD26\uDD44\uDD47\uDD50-\uDD72\uDD76\uDD83-\uDDB2\uDDC1-\uDDC4\uDDDA\uDDDC\uDE00-\uDE11\uDE13-\uDE2B\uDE80-\uDE86\uDE88\uDE8A-\uDE8D\uDE8F-\uDE9D\uDE9F-\uDEA8\uDEB0-\uDEDE\uDF05-\uDF0C\uDF0F\uDF10\uDF13-\uDF28\uDF2A-\uDF30\uDF32\uDF33\uDF35-\uDF39\uDF3D\uDF50\uDF5D-\uDF61]|\uD805[\uDC00-\uDC34\uDC47-\uDC4A\uDC5F-\uDC61\uDC80-\uDCAF\uDCC4\uDCC5\uDCC7\uDD80-\uDDAE\uDDD8-\uDDDB\uDE00-\uDE2F\uDE44\uDE80-\uDEAA\uDEB8\uDF00-\uDF1A\uDF40-\uDF46]|\uD806[\uDC00-\uDC2B\uDCA0-\uDCDF\uDCFF-\uDD06\uDD09\uDD0C-\uDD13\uDD15\uDD16\uDD18-\uDD2F\uDD3F\uDD41\uDDA0-\uDDA7\uDDAA-\uDDD0\uDDE1\uDDE3\uDE00\uDE0B-\uDE32\uDE3A\uDE50\uDE5C-\uDE89\uDE9D\uDEB0-\uDEF8]|\uD807[\uDC00-\uDC08\uDC0A-\uDC2E\uDC40\uDC72-\uDC8F\uDD00-\uDD06\uDD08\uDD09\uDD0B-\uDD30\uDD46\uDD60-\uDD65\uDD67\uDD68\uDD6A-\uDD89\uDD98\uDEE0-\uDEF2\uDFB0]|\uD808[\uDC00-\uDF99]|\uD809[\uDC80-\uDD43]|\uD80B[\uDF90-\uDFF0]|[\uD80C\uD81C-\uD820\uD822\uD840-\uD868\uD86A-\uD86C\uD86F-\uD872\uD874-\uD879\uD880-\uD883][\uDC00-\uDFFF]|\uD80D[\uDC00-\uDC2E]|\uD811[\uDC00-\uDE46]|\uD81A[\uDC00-\uDE38\uDE40-\uDE5E\uDE70-\uDEBE\uDED0-\uDEED\uDF00-\uDF2F\uDF40-\uDF43\uDF63-\uDF77\uDF7D-\uDF8F]|\uD81B[\uDE40-\uDE7F\uDF00-\uDF4A\uDF50\uDF93-\uDF9F\uDFE0\uDFE1\uDFE3]|\uD821[\uDC00-\uDFF7]|\uD823[\uDC00-\uDCD5\uDD00-\uDD08]|\uD82B[\uDFF0-\uDFF3\uDFF5-\uDFFB\uDFFD\uDFFE]|\uD82C[\uDC00-\uDD22\uDD50-\uDD52\uDD64-\uDD67\uDD70-\uDEFB]|\uD82F[\uDC00-\uDC6A\uDC70-\uDC7C\uDC80-\uDC88\uDC90-\uDC99]|\uD835[\uDC00-\uDC54\uDC56-\uDC9C\uDC9E\uDC9F\uDCA2\uDCA5\uDCA6\uDCA9-\uDCAC\uDCAE-\uDCB9\uDCBB\uDCBD-\uDCC3\uDCC5-\uDD05\uDD07-\uDD0A\uDD0D-\uDD14\uDD16-\uDD1C\uDD1E-\uDD39\uDD3B-\uDD3E\uDD40-\uDD44\uDD46\uDD4A-\uDD50\uDD52-\uDEA5\uDEA8-\uDEC0\uDEC2-\uDEDA\uDEDC-\uDEFA\uDEFC-\uDF14\uDF16-\uDF34\uDF36-\uDF4E\uDF50-\uDF6E\uDF70-\uDF88\uDF8A-\uDFA8\uDFAA-\uDFC2\uDFC4-\uDFCB]|\uD837[\uDF00-\uDF1E]|\uD838[\uDD00-\uDD2C\uDD37-\uDD3D\uDD4E\uDE90-\uDEAD\uDEC0-\uDEEB]|\uD839[\uDFE0-\uDFE6\uDFE8-\uDFEB\uDFED\uDFEE\uDFF0-\uDFFE]|\uD83A[\uDC00-\uDCC4\uDD00-\uDD43\uDD4B]|\uD83B[\uDE00-\uDE03\uDE05-\uDE1F\uDE21\uDE22\uDE24\uDE27\uDE29-\uDE32\uDE34-\uDE37\uDE39\uDE3B\uDE42\uDE47\uDE49\uDE4B\uDE4D-\uDE4F\uDE51\uDE52\uDE54\uDE57\uDE59\uDE5B\uDE5D\uDE5F\uDE61\uDE62\uDE64\uDE67-\uDE6A\uDE6C-\uDE72\uDE74-\uDE77\uDE79-\uDE7C\uDE7E\uDE80-\uDE89\uDE8B-\uDE9B\uDEA1-\uDEA3\uDEA5-\uDEA9\uDEAB-\uDEBB]|\uD869[\uDC00-\uDEDF\uDF00-\uDFFF]|\uD86D[\uDC00-\uDF38\uDF40-\uDFFF]|\uD86E[\uDC00-\uDC1D\uDC20-\uDFFF]|\uD873[\uDC00-\uDEA1\uDEB0-\uDFFF]|\uD87A[\uDC00-\uDFE0]|\uD87E[\uDC00-\uDE1D]|\uD884[\uDC00-\uDF4A])\S*/g;let du=(()=>{class n{transform(e){return e==null?null:(ba(n,e),e.replace(rw,r=>r[0].toUpperCase()+r.slice(1).toLowerCase()))}static ɵfac=function(r){return new(r||n)};static ɵpipe=it({name:"titlecase",type:n,pure:!0})}return n})(),hu=(()=>{class n{transform(e){return e==null?null:(ba(n,e),e.toUpperCase())}static ɵfac=function(r){return new(r||n)};static ɵpipe=it({name:"uppercase",type:n,pure:!0})}return n})();function ba(n,t){if(typeof t!="string")throw Xt()}const iw="mediumDate",ow=new on(""),sw=new on("");let pu=(()=>{class n{locale;defaultTimezone;defaultOptions;constructor(e,r,i){this.locale=e,this.defaultTimezone=r,this.defaultOptions=i}transform(e,r,i,o){if(e==null||e===""||e!==e)return null;try{const s=r??this.defaultOptions?.dateFormat??iw,a=i??this.defaultOptions?.timezone??this.defaultTimezone??void 0;return Iv(e,s,o||this.locale,a)}catch(s){throw Xt(n,s.message)}}static ɵfac=function(r){return new(r||n)(an(ue,16),an(ow,24),an(sw,24))};static ɵpipe=it({name:"date",type:n,pure:!0})}return n})();const aw=/#/g;let mu=(()=>{class n{_localization;constructor(e){this._localization=e}transform(e,r,i){if(e==null)return"";if(typeof r!="object"||r===null)throw Xt();const o=Bd(e,Object.keys(r),this._localization,i);return r[o].replace(aw,e.toString())}static ɵfac=function(r){return new(r||n)(an(va,16))};static ɵpipe=it({name:"i18nPlural",type:n,pure:!0})}return n})(),gu=(()=>{class n{transform(e,r){if(e==null)return"";if(typeof r!="object"||typeof e!="string")throw Xt();return r.hasOwnProperty(e)?r[e]:r.hasOwnProperty("other")?r.other:""}static ɵfac=function(r){return new(r||n)};static ɵpipe=it({name:"i18nSelect",type:n,pure:!0})}return n})(),yu=(()=>{class n{transform(e){return JSON.stringify(e,null,2)}static ɵfac=function(r){return new(r||n)};static ɵpipe=it({name:"json",type:n,pure:!1})}return n})();function lw(n,t){return{key:n,value:t}}let vu=(()=>{class n{differs;constructor(e){this.differs=e}differ;keyValues=[];compareFn=wu;transform(e,r=wu){if(!e||!(e instanceof Map)&&typeof e!="object")return null;this.differ??=this.differs.find(e).create();const i=this.differ.diff(e),o=r!==this.compareFn;return i&&(this.keyValues=[],i.forEachItem(s=>{this.keyValues.push(lw(s.key,s.currentValue))})),(i||o)&&(r&&this.keyValues.sort(r),this.compareFn=r),this.keyValues}static ɵfac=function(r){return new(r||n)(an(pa,16))};static ɵpipe=it({name:"keyvalue",type:n,pure:!1})}return n})();function wu(n,t){const e=n.key,r=t.key;if(e===r)return 0;if(e==null)return 1;if(r==null)return-1;if(typeof e=="string"&&typeof r=="string")return e<r?-1:1;if(typeof e=="number"&&typeof r=="number")return e-r;if(typeof e=="boolean"&&typeof r=="boolean")return e<r?-1:1;const i=String(e),o=String(r);return i==o?0:i<o?-1:1}let bu=(()=>{class n{_locale;constructor(e){this._locale=e}transform(e,r,i){if(!_a(e))return null;i||=this._locale;try{const o=Da(e);return Gv(o,i,r)}catch(o){throw Xt(n,o.message)}}static ɵfac=function(r){return new(r||n)(an(ue,16))};static ɵpipe=it({name:"number",type:n,pure:!0})}return n})(),_u=(()=>{class n{_locale;constructor(e){this._locale=e}transform(e,r,i){if(!_a(e))return null;i||=this._locale;try{const o=Da(e);return Zv(o,i,r)}catch(o){throw Xt(n,o.message)}}static ɵfac=function(r){return new(r||n)(an(ue,16))};static ɵpipe=it({name:"percent",type:n,pure:!0})}return n})(),Du=(()=>{class n{_locale;_defaultCurrencyCode;constructor(e,r="USD"){this._locale=e,this._defaultCurrencyCode=r}transform(e,r=this._defaultCurrencyCode,i="symbol",o,s){if(!_a(e))return null;s||=this._locale,typeof i=="boolean"&&(i=i?"symbol":"code");let a=r||this._defaultCurrencyCode;i!=="code"&&(i==="symbol"||i==="symbol-narrow"?a=Ev(a,i==="symbol"?"wide":"narrow",s):a=i);try{const l=Da(e);return Vv(l,s,a,r,o)}catch(l){throw Xt(n,l.message)}}static ɵfac=function(r){return new(r||n)(an(ue,16),an(G1,16))};static ɵpipe=it({name:"currency",type:n,pure:!0})}return n})();function _a(n){return!(n==null||n===""||n!==n)}function Da(n){if(typeof n=="string"&&!isNaN(Number(n)-parseFloat(n)))return Number(n);if(typeof n!="number")throw new V(2309,!1);return n}let Eu=(()=>{class n{transform(e,r,i){if(e==null)return null;if(!(typeof e=="string"||Array.isArray(e)))throw Xt();return e.slice(r,i)}static ɵfac=function(r){return new(r||n)};static ɵpipe=it({name:"slice",type:n,pure:!1})}return n})(),uw=(()=>{class n{static ɵfac=function(r){return new(r||n)};static ɵmod=Vy({type:n,imports:[Kl,nu,tu,ru,uu,lu,wi,ou,su,os,au,cu,hu,fu,yu,Eu,bu,_u,du,Du,pu,mu,gu,vu],exports:[Kl,nu,tu,ru,uu,lu,wi,ou,su,os,au,cu,hu,fu,yu,Eu,bu,_u,du,Du,pu,mu,gu,vu]});static ɵinj=Ap({})}return n})();function jd(n,t){t=encodeURIComponent(t);for(const e of n.split(";")){const r=e.indexOf("="),[i,o]=r==-1?[e,""]:[e.slice(0,r),e.slice(r+1)];if(i.trim()===t)return decodeURIComponent(o)}return null}class Hd{}const cw="browser";class Ud{_doc;constructor(t){this._doc=t}manager}let ss=(()=>{class n extends Ud{constructor(e){super(e)}supports(e){return!0}addEventListener(e,r,i,o){return e.addEventListener(r,i,o),()=>this.removeEventListener(e,r,i,o)}removeEventListener(e,r,i,o){return e.removeEventListener(r,i,o)}static ɵfac=function(r){return new(r||n)(cn(se))};static ɵprov=fn({token:n,factory:n.ɵfac})}return n})();const as=new on("");let zd=(()=>{class n{_zone;_plugins;_eventNameToPlugin=new Map;constructor(e,r){this._zone=r,e.forEach(s=>{s.manager=this});const i=e.filter(s=>!(s instanceof ss));this._plugins=i.slice().reverse();const o=e.find(s=>s instanceof ss);o&&this._plugins.push(o)}addEventListener(e,r,i,o){return this._findPluginFor(r).addEventListener(e,r,i,o)}getZone(){return this._zone}_findPluginFor(e){let r=this._eventNameToPlugin.get(e);if(r)return r;if(r=this._plugins.find(o=>o.supports(e)),!r)throw new V(5101,!1);return this._eventNameToPlugin.set(e,r),r}static ɵfac=function(r){return new(r||n)(cn(as),cn(St))};static ɵprov=fn({token:n,factory:n.ɵfac})}return n})();const yo="ng-app-id";function Cu(n){for(const t of n)t.remove()}function ku(n,t){const e=t.createElement("style");return e.textContent=n,e}function fw(n,t,e,r){const i=n.head?.querySelectorAll(`style[${yo}="${t}"],link[${yo}="${t}"]`);if(i)for(const o of i)o.removeAttribute(yo),o instanceof HTMLLinkElement?r.set(o.href.slice(o.href.lastIndexOf("/")+1),{usage:0,elements:[o]}):o.textContent&&e.set(o.textContent,{usage:0,elements:[o]})}function ls(n,t){const e=t.createElement("link");return e.setAttribute("rel","stylesheet"),e.setAttribute("href",n),e}let Vd=(()=>{class n{doc;appId;nonce;inline=new Map;external=new Map;hosts=new Set;constructor(e,r,i,o={}){this.doc=e,this.appId=r,this.nonce=i,fw(e,r,this.inline,this.external),this.hosts.add(e.head)}addStyles(e,r){for(const i of e)this.addUsage(i,this.inline,ku);r?.forEach(i=>this.addUsage(i,this.external,ls))}removeStyles(e,r){for(const i of e)this.removeUsage(i,this.inline);r?.forEach(i=>this.removeUsage(i,this.external))}addUsage(e,r,i){const o=r.get(e);o?o.usage++:r.set(e,{usage:1,elements:[...this.hosts].map(s=>this.addElement(s,i(e,this.doc)))})}removeUsage(e,r){const i=r.get(e);i&&(i.usage--,i.usage<=0&&(Cu(i.elements),r.delete(e)))}ngOnDestroy(){for(const[,{elements:e}]of[...this.inline,...this.external])Cu(e);this.hosts.clear()}addHost(e){this.hosts.add(e);for(const[r,{elements:i}]of this.inline)i.push(this.addElement(e,ku(r,this.doc)));for(const[r,{elements:i}]of this.external)i.push(this.addElement(e,ls(r,this.doc)))}removeHost(e){this.hosts.delete(e)}addElement(e,r){return this.nonce&&r.setAttribute("nonce",this.nonce),e.appendChild(r)}static ɵfac=function(r){return new(r||n)(cn(se),cn(Df),cn(kf,8),cn(Cf))};static ɵprov=fn({token:n,factory:n.ɵfac})}return n})();const vo={svg:"http://www.w3.org/2000/svg",xhtml:"http://www.w3.org/1999/xhtml",xlink:"http://www.w3.org/1999/xlink",xml:"http://www.w3.org/XML/1998/namespace",xmlns:"http://www.w3.org/2000/xmlns/",math:"http://www.w3.org/1998/Math/MathML"},Ea=/%COMP%/g,Zd="%COMP%",dw=`_nghost-${Zd}`,hw=`_ngcontent-${Zd}`,pw=!0,mw=new on("",{factory:()=>pw});function gw(n){return hw.replace(Ea,n)}function yw(n){return dw.replace(Ea,n)}function Gd(n,t){return t.map(e=>e.replace(Ea,n))}let xu=(()=>{class n{eventManager;sharedStylesHost;appId;removeStylesOnCompDestroy;doc;ngZone;nonce;tracingService;rendererByCompId=new Map;defaultRenderer;constructor(e,r,i,o,s,a,l=null,u=null){this.eventManager=e,this.sharedStylesHost=r,this.appId=i,this.removeStylesOnCompDestroy=o,this.doc=s,this.ngZone=a,this.nonce=l,this.tracingService=u,this.defaultRenderer=new Ca(e,s,a,this.tracingService)}createRenderer(e,r){if(!e||!r)return this.defaultRenderer;const i=this.getOrCreateRenderer(e,r);return i instanceof Au?i.applyToHost(e):i instanceof us&&i.applyStyles(),i}getOrCreateRenderer(e,r){const i=this.rendererByCompId;let o=i.get(r.id);if(!o){const s=this.doc,a=this.ngZone,l=this.eventManager,u=this.sharedStylesHost,c=this.removeStylesOnCompDestroy,f=this.tracingService;switch(r.encapsulation){case Ht.Emulated:o=new Au(l,u,r,this.appId,c,s,a,f);break;case Ht.ShadowDom:return new Iu(l,e,r,s,a,this.nonce,f,u);case Ht.ExperimentalIsolatedShadowDom:return new Iu(l,e,r,s,a,this.nonce,f);default:o=new us(l,u,r,c,s,a,f);break}i.set(r.id,o)}return o}ngOnDestroy(){this.rendererByCompId.clear()}componentReplaced(e){this.rendererByCompId.delete(e)}static ɵfac=function(r){return new(r||n)(cn(zd),cn(Vd),cn(Df),cn(mw),cn(se),cn(St),cn(kf),cn(Ai,8))};static ɵprov=fn({token:n,factory:n.ɵfac})}return n})();class Ca{eventManager;doc;ngZone;tracingService;data=Object.create(null);throwOnSyntheticProps=!0;constructor(t,e,r,i){this.eventManager=t,this.doc=e,this.ngZone=r,this.tracingService=i}destroy(){}destroyNode=null;createElement(t,e){return e?this.doc.createElementNS(vo[e]||e,t):this.doc.createElement(t)}createComment(t){return this.doc.createComment(t)}createText(t){return this.doc.createTextNode(t)}appendChild(t,e){(Tu(t)?t.content:t).appendChild(e)}insertBefore(t,e,r){t&&(Tu(t)?t.content:t).insertBefore(e,r)}removeChild(t,e){e.remove()}selectRootElement(t,e){let r=typeof t=="string"?this.doc.querySelector(t):t;if(!r)throw new V(-5104,!1);return e||(r.textContent=""),r}parentNode(t){return t.parentNode}nextSibling(t){return t.nextSibling}setAttribute(t,e,r,i){if(i){e=i+":"+e;const o=vo[i];o?t.setAttributeNS(o,e,r):t.setAttribute(e,r)}else t.setAttribute(e,r)}removeAttribute(t,e,r){if(r){const i=vo[r];i?t.removeAttributeNS(i,e):t.removeAttribute(`${r}:${e}`)}else t.removeAttribute(e)}addClass(t,e){t.classList.add(e)}removeClass(t,e){t.classList.remove(e)}setStyle(t,e,r,i){i&(Kt.DashCase|Kt.Important)?t.style.setProperty(e,r,i&Kt.Important?"important":""):t.style[e]=r}removeStyle(t,e,r){r&Kt.DashCase?t.style.removeProperty(e):t.style[e]=""}setProperty(t,e,r){t!=null&&(t[e]=r)}setValue(t,e){t.nodeValue=e}listen(t,e,r,i){if(typeof t=="string"&&(t=ur().getGlobalEventTarget(this.doc,t),!t))throw new V(5102,!1);let o=this.decoratePreventDefault(r);return this.tracingService?.wrapEventListener&&(o=this.tracingService.wrapEventListener(t,e,o)),this.eventManager.addEventListener(t,e,o,i)}decoratePreventDefault(t){return e=>{if(e==="__ngUnwrap__")return t;t(e)===!1&&e.preventDefault()}}}function Tu(n){return n.tagName==="TEMPLATE"&&n.content!==void 0}class Iu extends Ca{hostEl;sharedStylesHost;shadowRoot;constructor(t,e,r,i,o,s,a,l){super(t,i,o,a),this.hostEl=e,this.sharedStylesHost=l,this.shadowRoot=e.attachShadow({mode:"open"}),this.sharedStylesHost&&this.sharedStylesHost.addHost(this.shadowRoot);let u=r.styles;u=Gd(r.id,u);for(const f of u){const b=document.createElement("style");s&&b.setAttribute("nonce",s),b.textContent=f,this.shadowRoot.appendChild(b)}const c=r.getExternalStyles?.();if(c)for(const f of c){const b=ls(f,i);s&&b.setAttribute("nonce",s),this.shadowRoot.appendChild(b)}}nodeOrShadowRoot(t){return t===this.hostEl?this.shadowRoot:t}appendChild(t,e){return super.appendChild(this.nodeOrShadowRoot(t),e)}insertBefore(t,e,r){return super.insertBefore(this.nodeOrShadowRoot(t),e,r)}removeChild(t,e){return super.removeChild(null,e)}parentNode(t){return this.nodeOrShadowRoot(super.parentNode(this.nodeOrShadowRoot(t)))}destroy(){this.sharedStylesHost&&this.sharedStylesHost.removeHost(this.shadowRoot)}}class us extends Ca{sharedStylesHost;removeStylesOnCompDestroy;styles;styleUrls;constructor(t,e,r,i,o,s,a,l){super(t,o,s,a),this.sharedStylesHost=e,this.removeStylesOnCompDestroy=i;let u=r.styles;this.styles=l?Gd(l,u):u,this.styleUrls=r.getExternalStyles?.(l)}applyStyles(){this.sharedStylesHost.addStyles(this.styles,this.styleUrls)}destroy(){this.removeStylesOnCompDestroy&&ze.size===0&&this.sharedStylesHost.removeStyles(this.styles,this.styleUrls)}}class Au extends us{contentAttr;hostAttr;constructor(t,e,r,i,o,s,a,l){const u=i+"-"+r.id;super(t,e,r,o,s,a,l,u),this.contentAttr=gw(u),this.hostAttr=yw(u)}applyToHost(t){this.applyStyles(),this.setAttribute(t,this.hostAttr,"")}createElement(t,e){const r=super.createElement(t,e);return super.setAttribute(r,this.contentAttr,""),r}}class ka extends dv{supportsDOMEvents=!0;static makeCurrent(){fv(new ka)}onAndCancel(t,e,r,i){return t.addEventListener(e,r,i),()=>{t.removeEventListener(e,r,i)}}dispatchEvent(t,e){t.dispatchEvent(e)}remove(t){t.remove()}createElement(t,e){return e=e||this.getDefaultDocument(),e.createElement(t)}createHtmlDocument(){return document.implementation.createHTMLDocument("fakeTitle")}getDefaultDocument(){return document}isElementNode(t){return t.nodeType===Node.ELEMENT_NODE}isShadowRoot(t){return t instanceof DocumentFragment}getGlobalEventTarget(t,e){return e==="window"?window:e==="document"?t:e==="body"?t.body:null}getBaseHref(t){const e=vw();return e==null?null:ww(e)}resetBaseElement(){sr=null}getUserAgent(){return window.navigator.userAgent}getCookie(t){return jd(document.cookie,t)}}let sr=null;function vw(){return sr=sr||document.head.querySelector("base"),sr?sr.getAttribute("href"):null}function ww(n){return new URL(n,document.baseURI).pathname}let bw=(()=>{class n{build(){return new XMLHttpRequest}static ɵfac=function(r){return new(r||n)};static ɵprov=fn({token:n,factory:n.ɵfac})}return n})();const Su=["alt","control","meta","shift"],_w={"\b":"Backspace","	":"Tab","":"Delete","\x1B":"Escape",Del:"Delete",Esc:"Escape",Left:"ArrowLeft",Right:"ArrowRight",Up:"ArrowUp",Down:"ArrowDown",Menu:"ContextMenu",Scroll:"ScrollLock",Win:"OS"},Dw={alt:n=>n.altKey,control:n=>n.ctrlKey,meta:n=>n.metaKey,shift:n=>n.shiftKey};let Ew=(()=>{class n extends Ud{constructor(e){super(e)}supports(e){return n.parseEventName(e)!=null}addEventListener(e,r,i,o){const s=n.parseEventName(r),a=n.eventCallback(s.fullKey,i,this.manager.getZone());return this.manager.getZone().runOutsideAngular(()=>ur().onAndCancel(e,s.domEventName,a,o))}static parseEventName(e){const r=e.toLowerCase().split("."),i=r.shift();if(r.length===0||!(i==="keydown"||i==="keyup"))return null;const o=n._normalizeKey(r.pop());let s="",a=r.indexOf("code");if(a>-1&&(r.splice(a,1),s="code."),Su.forEach(u=>{const c=r.indexOf(u);c>-1&&(r.splice(c,1),s+=u+".")}),s+=o,r.length!=0||o.length===0)return null;const l={};return l.domEventName=i,l.fullKey=s,l}static matchEventFullKeyCode(e,r){let i=_w[e.key]||e.key,o="";return r.indexOf("code.")>-1&&(i=e.code,o="code."),i==null||!i?!1:(i=i.toLowerCase(),i===" "?i="space":i==="."&&(i="dot"),Su.forEach(s=>{if(s!==i){const a=Dw[s];a(e)&&(o+=s+".")}}),o+=i,o===r)}static eventCallback(e,r,i){return o=>{n.matchEventFullKeyCode(o,e)&&i.runGuarded(()=>r(o))}}static _normalizeKey(e){return e==="esc"?"escape":e}static ɵfac=function(r){return new(r||n)(cn(se))};static ɵprov=fn({token:n,factory:n.ɵfac})}return n})();async function Cw(n,t,e){const r={rootComponent:n,...kw(t,e)};return cv(r)}function kw(n,t){return{platformRef:t?.platformRef,appProviders:[...Sw,...n?.providers??[]],platformProviders:Aw}}function xw(){ka.makeCurrent()}function Tw(){return new ki}function Iw(){return Cm(document),document}const Aw=[{provide:Cf,useValue:cw},{provide:Ef,useValue:xw,multi:!0},{provide:se,useFactory:Iw}],Sw=[{provide:Ts,useValue:"root"},{provide:ki,useFactory:Tw},{provide:as,useClass:ss,multi:!0},{provide:as,useClass:Ew,multi:!0},xu,Vd,zd,{provide:ia,useExisting:xu},{provide:Hd,useClass:bw},[]];class Ut{headers;normalizedNames=new Map;lazyInit;lazyUpdate=null;constructor(t){t?typeof t=="string"?this.lazyInit=()=>{this.headers=new Map,t.split(`
`).forEach(e=>{const r=e.indexOf(":");if(r>0){const i=e.slice(0,r),o=e.slice(r+1).trim();this.addHeaderEntry(i,o)}})}:typeof Headers<"u"&&t instanceof Headers?(this.headers=new Map,t.forEach((e,r)=>{this.addHeaderEntry(r,e)})):this.lazyInit=()=>{this.headers=new Map,Object.entries(t).forEach(([e,r])=>{this.setHeaderEntries(e,r)})}:this.headers=new Map}has(t){return this.init(),this.headers.has(t.toLowerCase())}get(t){this.init();const e=this.headers.get(t.toLowerCase());return e&&e.length>0?e[0]:null}keys(){return this.init(),Array.from(this.normalizedNames.values())}getAll(t){return this.init(),this.headers.get(t.toLowerCase())||null}append(t,e){return this.clone({name:t,value:e,op:"a"})}set(t,e){return this.clone({name:t,value:e,op:"s"})}delete(t,e){return this.clone({name:t,value:e,op:"d"})}maybeSetNormalizedName(t,e){this.normalizedNames.has(e)||this.normalizedNames.set(e,t)}init(){this.lazyInit&&(this.lazyInit instanceof Ut?this.copyFrom(this.lazyInit):this.lazyInit(),this.lazyInit=null,this.lazyUpdate&&(this.lazyUpdate.forEach(t=>this.applyUpdate(t)),this.lazyUpdate=null))}copyFrom(t){t.init(),Array.from(t.headers.keys()).forEach(e=>{this.headers.set(e,t.headers.get(e)),this.normalizedNames.set(e,t.normalizedNames.get(e))})}clone(t){const e=new Ut;return e.lazyInit=this.lazyInit&&this.lazyInit instanceof Ut?this.lazyInit:this,e.lazyUpdate=(this.lazyUpdate||[]).concat([t]),e}applyUpdate(t){const e=t.name.toLowerCase();switch(t.op){case"a":case"s":let r=t.value;if(typeof r=="string"&&(r=[r]),r.length===0)return;this.maybeSetNormalizedName(t.name,e);const i=(t.op==="a"?this.headers.get(e):void 0)||[];i.push(...r),this.headers.set(e,i);break;case"d":const o=t.value;if(!o)this.headers.delete(e),this.normalizedNames.delete(e);else{let s=this.headers.get(e);if(!s)return;s=s.filter(a=>o.indexOf(a)===-1),s.length===0?(this.headers.delete(e),this.normalizedNames.delete(e)):this.headers.set(e,s)}break}}addHeaderEntry(t,e){const r=t.toLowerCase();this.maybeSetNormalizedName(t,r),this.headers.has(r)?this.headers.get(r).push(e):this.headers.set(r,[e])}setHeaderEntries(t,e){const r=(Array.isArray(e)?e:[e]).map(o=>o.toString()),i=t.toLowerCase();this.headers.set(i,r),this.maybeSetNormalizedName(t,i)}forEach(t){this.init(),Array.from(this.normalizedNames.keys()).forEach(e=>t(this.normalizedNames.get(e),this.headers.get(e)))}}class Fw{map=new Map;set(t,e){return this.map.set(t,e),this}get(t){return this.map.has(t)||this.map.set(t,t.defaultValue()),this.map.get(t)}delete(t){return this.map.delete(t),this}has(t){return this.map.has(t)}keys(){return this.map.keys()}}class Rw{encodeKey(t){return Fu(t)}encodeValue(t){return Fu(t)}decodeKey(t){return decodeURIComponent(t)}decodeValue(t){return decodeURIComponent(t)}}function Ow(n,t){const e=new Map;return n.length>0&&n.replace(/^\?/,"").split("&").forEach(i=>{const o=i.indexOf("="),[s,a]=o==-1?[t.decodeKey(i),""]:[t.decodeKey(i.slice(0,o)),t.decodeValue(i.slice(o+1))],l=e.get(s)||[];l.push(a),e.set(s,l)}),e}const Pw=/%(\d[a-f0-9])/gi,Mw={40:"@","3A":":",24:"$","2C":",","3B":";","3D":"=","3F":"?","2F":"/"};function Fu(n){return encodeURIComponent(n).replace(Pw,(t,e)=>Mw[e]??t)}function Gr(n){return`${n}`}class ee{map;encoder;updates=null;cloneFrom=null;constructor(t={}){if(this.encoder=t.encoder||new Rw,t.fromString){if(t.fromObject)throw new V(2805,!1);this.map=Ow(t.fromString,this.encoder)}else t.fromObject?(this.map=new Map,Object.keys(t.fromObject).forEach(e=>{const r=t.fromObject[e],i=Array.isArray(r)?r.map(Gr):[Gr(r)];this.map.set(e,i)})):this.map=null}has(t){return this.init(),this.map.has(t)}get(t){this.init();const e=this.map.get(t);return e?e[0]:null}getAll(t){return this.init(),this.map.get(t)||null}keys(){return this.init(),Array.from(this.map.keys())}append(t,e){return this.clone({param:t,value:e,op:"a"})}appendAll(t){const e=[];return Object.keys(t).forEach(r=>{const i=t[r];Array.isArray(i)?i.forEach(o=>{e.push({param:r,value:o,op:"a"})}):e.push({param:r,value:i,op:"a"})}),this.clone(e)}set(t,e){return this.clone({param:t,value:e,op:"s"})}delete(t,e){return this.clone({param:t,value:e,op:"d"})}toString(){return this.init(),this.keys().map(t=>{const e=this.encoder.encodeKey(t);return this.map.get(t).map(r=>e+"="+this.encoder.encodeValue(r)).join("&")}).filter(t=>t!=="").join("&")}clone(t){const e=new ee({encoder:this.encoder});return e.cloneFrom=this.cloneFrom||this,e.updates=(this.updates||[]).concat(t),e}init(){this.map===null&&(this.map=new Map),this.cloneFrom!==null&&(this.cloneFrom.init(),this.cloneFrom.keys().forEach(t=>this.map.set(t,this.cloneFrom.map.get(t))),this.updates.forEach(t=>{switch(t.op){case"a":case"s":const e=(t.op==="a"?this.map.get(t.param):void 0)||[];e.push(Gr(t.value)),this.map.set(t.param,e);break;case"d":if(t.value!==void 0){let r=this.map.get(t.param)||[];const i=r.indexOf(Gr(t.value));i!==-1&&r.splice(i,1),r.length>0?this.map.set(t.param,r):this.map.delete(t.param)}else{this.map.delete(t.param);break}}}),this.cloneFrom=this.updates=null)}}function Lw(n){switch(n){case"DELETE":case"GET":case"HEAD":case"OPTIONS":case"JSONP":return!1;default:return!0}}function Ru(n){return typeof ArrayBuffer<"u"&&n instanceof ArrayBuffer}function Ou(n){return typeof Blob<"u"&&n instanceof Blob}function Pu(n){return typeof FormData<"u"&&n instanceof FormData}function Nw(n){return typeof URLSearchParams<"u"&&n instanceof URLSearchParams}const Mu="Content-Type",Lu="Accept",$d="text/plain",Wd="application/json",Bw=`${Wd}, ${$d}, */*`;class fr{url;body=null;headers;context;reportProgress=!1;withCredentials=!1;credentials;keepalive=!1;cache;priority;mode;redirect;referrer;integrity;referrerPolicy;responseType="json";method;params;urlWithParams;transferCache;timeout;constructor(t,e,r,i){this.url=e,this.method=t.toUpperCase();let o;if(Lw(this.method)||i?(this.body=r!==void 0?r:null,o=i):o=r,o){if(this.reportProgress=!!o.reportProgress,this.withCredentials=!!o.withCredentials,this.keepalive=!!o.keepalive,o.responseType&&(this.responseType=o.responseType),o.headers&&(this.headers=o.headers),o.context&&(this.context=o.context),o.params&&(this.params=o.params),o.priority&&(this.priority=o.priority),o.cache&&(this.cache=o.cache),o.credentials&&(this.credentials=o.credentials),typeof o.timeout=="number"){if(o.timeout<1||!Number.isInteger(o.timeout))throw new V(2822,"");this.timeout=o.timeout}o.mode&&(this.mode=o.mode),o.redirect&&(this.redirect=o.redirect),o.integrity&&(this.integrity=o.integrity),o.referrer&&(this.referrer=o.referrer),o.referrerPolicy&&(this.referrerPolicy=o.referrerPolicy),this.transferCache=o.transferCache}if(this.headers??=new Ut,this.context??=new Fw,!this.params)this.params=new ee,this.urlWithParams=e;else{const s=this.params.toString();if(s.length===0)this.urlWithParams=e;else{const a=e.indexOf("?"),l=a===-1?"?":a<e.length-1?"&":"";this.urlWithParams=e+l+s}}}serializeBody(){return this.body===null?null:typeof this.body=="string"||Ru(this.body)||Ou(this.body)||Pu(this.body)||Nw(this.body)?this.body:this.body instanceof ee?this.body.toString():typeof this.body=="object"||typeof this.body=="boolean"||Array.isArray(this.body)?JSON.stringify(this.body):this.body.toString()}detectContentTypeHeader(){return this.body===null||Pu(this.body)?null:Ou(this.body)?this.body.type||null:Ru(this.body)?null:typeof this.body=="string"?$d:this.body instanceof ee?"application/x-www-form-urlencoded;charset=UTF-8":typeof this.body=="object"||typeof this.body=="number"||typeof this.body=="boolean"?Wd:null}clone(t={}){const e=t.method||this.method,r=t.url||this.url,i=t.responseType||this.responseType,o=t.keepalive??this.keepalive,s=t.priority||this.priority,a=t.cache||this.cache,l=t.mode||this.mode,u=t.redirect||this.redirect,c=t.credentials||this.credentials,f=t.referrer||this.referrer,b=t.integrity||this.integrity,p=t.referrerPolicy||this.referrerPolicy,E=t.transferCache??this.transferCache,A=t.timeout??this.timeout,F=t.body!==void 0?t.body:this.body,S=t.withCredentials??this.withCredentials,gn=t.reportProgress??this.reportProgress;let tt=t.headers||this.headers,kn=t.params||this.params;const ot=t.context??this.context;return t.setHeaders!==void 0&&(tt=Object.keys(t.setHeaders).reduce((Rt,Vn)=>Rt.set(Vn,t.setHeaders[Vn]),tt)),t.setParams&&(kn=Object.keys(t.setParams).reduce((Rt,Vn)=>Rt.set(Vn,t.setParams[Vn]),kn)),new fr(e,r,F,{params:kn,headers:tt,context:ot,reportProgress:gn,responseType:i,withCredentials:S,transferCache:E,keepalive:o,cache:a,priority:s,timeout:A,mode:l,redirect:u,credentials:c,referrer:f,integrity:b,referrerPolicy:p})}}var Me=(function(n){return n[n.Sent=0]="Sent",n[n.UploadProgress=1]="UploadProgress",n[n.ResponseHeader=2]="ResponseHeader",n[n.DownloadProgress=3]="DownloadProgress",n[n.Response=4]="Response",n[n.User=5]="User",n})(Me||{});class xa{headers;status;statusText;url;ok;type;redirected;responseType;constructor(t,e=200,r="OK"){this.headers=t.headers||new Ut,this.status=t.status!==void 0?t.status:e,this.statusText=t.statusText||r,this.url=t.url||null,this.redirected=t.redirected,this.responseType=t.responseType,this.ok=this.status>=200&&this.status<300}}class Ta extends xa{constructor(t={}){super(t)}type=Me.ResponseHeader;clone(t={}){return new Ta({headers:t.headers||this.headers,status:t.status!==void 0?t.status:this.status,statusText:t.statusText||this.statusText,url:t.url||this.url||void 0})}}class Ir extends xa{body;constructor(t={}){super(t),this.body=t.body!==void 0?t.body:null}type=Me.Response;clone(t={}){return new Ir({body:t.body!==void 0?t.body:this.body,headers:t.headers||this.headers,status:t.status!==void 0?t.status:this.status,statusText:t.statusText||this.statusText,url:t.url||this.url||void 0,redirected:t.redirected??this.redirected,responseType:t.responseType??this.responseType})}}class wo extends xa{name="HttpErrorResponse";message;error;ok=!1;constructor(t){super(t,0,"Unknown Error"),this.status>=200&&this.status<300?this.message=`Http failure during parsing for ${t.url||"(unknown url)"}`:this.message=`Http failure response for ${t.url||"(unknown url)"}: ${t.status} ${t.statusText}`,this.error=t.error||null}}const jw=200,Hw=204,Uw=new on(""),zw=/^\)\]\}',?\n/;let Yd=(()=>{class n{xhrFactory;tracingService=z(Ai,{optional:!0});constructor(e){this.xhrFactory=e}maybePropagateTrace(e){return this.tracingService?.propagate?this.tracingService.propagate(e):e}handle(e){if(e.method==="JSONP")throw new V(-2800,!1);const r=this.xhrFactory;return fc(null).pipe(bp(()=>new Kn(o=>{const s=r.build();if(s.open(e.method,e.urlWithParams),e.withCredentials&&(s.withCredentials=!0),e.headers.forEach((F,S)=>s.setRequestHeader(F,S.join(","))),e.headers.has(Lu)||s.setRequestHeader(Lu,Bw),!e.headers.has(Mu)){const F=e.detectContentTypeHeader();F!==null&&s.setRequestHeader(Mu,F)}if(e.timeout&&(s.timeout=e.timeout),e.responseType){const F=e.responseType.toLowerCase();s.responseType=F!=="json"?F:"text"}const a=e.serializeBody();let l=null;const u=()=>{if(l!==null)return l;const F=s.statusText||"OK",S=new Ut(s.getAllResponseHeaders()),gn=s.responseURL||e.url;return l=new Ta({headers:S,status:s.status,statusText:F,url:gn}),l},c=this.maybePropagateTrace(()=>{let{headers:F,status:S,statusText:gn,url:tt}=u(),kn=null;S!==Hw&&(kn=typeof s.response>"u"?s.responseText:s.response),S===0&&(S=kn?jw:0);let ot=S>=200&&S<300;if(e.responseType==="json"&&typeof kn=="string"){const Rt=kn;kn=kn.replace(zw,"");try{kn=kn!==""?JSON.parse(kn):null}catch(Vn){kn=Rt,ot&&(ot=!1,kn={error:Vn,text:kn})}}ot?(o.next(new Ir({body:kn,headers:F,status:S,statusText:gn,url:tt||void 0})),o.complete()):o.error(new wo({error:kn,headers:F,status:S,statusText:gn,url:tt||void 0}))}),f=this.maybePropagateTrace(F=>{const{url:S}=u(),gn=new wo({error:F,status:s.status||0,statusText:s.statusText||"Unknown Error",url:S||void 0});o.error(gn)});let b=f;e.timeout&&(b=this.maybePropagateTrace(F=>{const{url:S}=u(),gn=new wo({error:new DOMException("Request timed out","TimeoutError"),status:s.status||0,statusText:s.statusText||"Request timeout",url:S||void 0});o.error(gn)}));let p=!1;const E=this.maybePropagateTrace(F=>{p||(o.next(u()),p=!0);let S={type:Me.DownloadProgress,loaded:F.loaded};F.lengthComputable&&(S.total=F.total),e.responseType==="text"&&s.responseText&&(S.partialText=s.responseText),o.next(S)}),A=this.maybePropagateTrace(F=>{let S={type:Me.UploadProgress,loaded:F.loaded};F.lengthComputable&&(S.total=F.total),o.next(S)});return s.addEventListener("load",c),s.addEventListener("error",f),s.addEventListener("timeout",b),s.addEventListener("abort",f),e.reportProgress&&(s.addEventListener("progress",E),a!==null&&s.upload&&s.upload.addEventListener("progress",A)),s.send(a),o.next({type:Me.Sent}),()=>{s.removeEventListener("error",f),s.removeEventListener("abort",f),s.removeEventListener("load",c),s.removeEventListener("timeout",b),e.reportProgress&&(s.removeEventListener("progress",E),a!==null&&s.upload&&s.upload.removeEventListener("progress",A)),s.readyState!==s.DONE&&s.abort()}})))}static ɵfac=function(r){return new(r||n)(cn(Hd))};static ɵprov=fn({token:n,factory:n.ɵfac,providedIn:"root"})}return n})();function Vw(n,t){return t(n)}function Zw(n,t,e){return(r,i)=>Ss(e,()=>t(r,o=>n(o,i)))}const Ia=new on("",{factory:()=>[]}),Gw=new on(""),$w=new on("",{factory:()=>!0});let Xd=(()=>{class n{static ɵfac=function(r){return new(r||n)};static ɵprov=fn({token:n,factory:function(r){let i=null;return r?i=new(r||n):i=cn(Yd),i},providedIn:"root"})}return n})(),cs=(()=>{class n{backend;injector;chain=null;pendingTasks=z(W0);contributeToStability=z($w);constructor(e,r){this.backend=e,this.injector=r}handle(e){if(this.chain===null){const r=Array.from(new Set([...this.injector.get(Ia),...this.injector.get(Gw,[])]));this.chain=r.reduceRight((i,o)=>Zw(i,o,this.injector),Vw)}if(this.contributeToStability){const r=this.pendingTasks.add();return this.chain(e,i=>this.backend.handle(i)).pipe(wp(r))}else return this.chain(e,r=>this.backend.handle(r))}static ɵfac=function(r){return new(r||n)(cn(Xd),cn(Vt))};static ɵprov=fn({token:n,factory:n.ɵfac,providedIn:"root"})}return n})(),qd=(()=>{class n{static ɵfac=function(r){return new(r||n)};static ɵprov=fn({token:n,factory:function(r){let i=null;return r?i=new(r||n):i=cn(cs),i},providedIn:"root"})}return n})();function bo(n,t){return{body:t,headers:n.headers,context:n.context,observe:n.observe,params:n.params,reportProgress:n.reportProgress,responseType:n.responseType,withCredentials:n.withCredentials,credentials:n.credentials,transferCache:n.transferCache,timeout:n.timeout,keepalive:n.keepalive,priority:n.priority,cache:n.cache,mode:n.mode,redirect:n.redirect,integrity:n.integrity,referrer:n.referrer,referrerPolicy:n.referrerPolicy}}let Qd=(()=>{class n{handler;constructor(e){this.handler=e}request(e,r,i={}){let o;if(e instanceof fr)o=e;else{let l;i.headers instanceof Ut?l=i.headers:l=new Ut(i.headers);let u;i.params&&(i.params instanceof ee?u=i.params:u=new ee({fromObject:i.params})),o=new fr(e,r,i.body!==void 0?i.body:null,{headers:l,context:i.context,params:u,reportProgress:i.reportProgress,responseType:i.responseType||"json",withCredentials:i.withCredentials,transferCache:i.transferCache,keepalive:i.keepalive,priority:i.priority,cache:i.cache,mode:i.mode,redirect:i.redirect,credentials:i.credentials,referrer:i.referrer,referrerPolicy:i.referrerPolicy,integrity:i.integrity,timeout:i.timeout})}const s=fc(o).pipe(vp(l=>this.handler.handle(l)));if(e instanceof fr||i.observe==="events")return s;const a=s.pipe(yp(l=>l instanceof Ir));switch(i.observe||"body"){case"body":switch(o.responseType){case"arraybuffer":return a.pipe(Te(l=>{if(l.body!==null&&!(l.body instanceof ArrayBuffer))throw new V(2806,!1);return l.body}));case"blob":return a.pipe(Te(l=>{if(l.body!==null&&!(l.body instanceof Blob))throw new V(2807,!1);return l.body}));case"text":return a.pipe(Te(l=>{if(l.body!==null&&typeof l.body!="string")throw new V(2808,!1);return l.body}));default:return a.pipe(Te(l=>l.body))}case"response":return a;default:throw new V(2809,!1)}}delete(e,r={}){return this.request("DELETE",e,r)}get(e,r={}){return this.request("GET",e,r)}head(e,r={}){return this.request("HEAD",e,r)}jsonp(e,r){return this.request("JSONP",e,{params:new ee().append(r,"JSONP_CALLBACK"),observe:"body",responseType:"json"})}options(e,r={}){return this.request("OPTIONS",e,r)}patch(e,r,i={}){return this.request("PATCH",e,bo(i,r))}post(e,r,i={}){return this.request("POST",e,bo(i,r))}put(e,r,i={}){return this.request("PUT",e,bo(i,r))}static ɵfac=function(r){return new(r||n)(cn(qd))};static ɵprov=fn({token:n,factory:n.ɵfac,providedIn:"root"})}return n})();const Ww=new on("",{factory:()=>!0}),Yw="XSRF-TOKEN",Xw=new on("",{factory:()=>Yw}),qw="X-XSRF-TOKEN",Qw=new on("",{factory:()=>qw});let Jw=(()=>{class n{cookieName=z(Xw);doc=z(se);lastCookieString="";lastToken=null;parseCount=0;getToken(){const e=this.doc.cookie||"";return e!==this.lastCookieString&&(this.parseCount++,this.lastToken=jd(e,this.cookieName),this.lastCookieString=e),this.lastToken}static ɵfac=function(r){return new(r||n)};static ɵprov=fn({token:n,factory:n.ɵfac,providedIn:"root"})}return n})(),Kw=(()=>{class n{static ɵfac=function(r){return new(r||n)};static ɵprov=fn({token:n,factory:function(r){let i=null;return r?i=new(r||n):i=cn(Jw),i},providedIn:"root"})}return n})();function nb(n,t){if(!z(Ww)||n.method==="GET"||n.method==="HEAD")return t(n);try{const i=z(Od).href,{origin:o}=new URL(i),{origin:s}=new URL(n.url,o);if(o!==s)return t(n)}catch{return t(n)}const e=z(Kw).getToken(),r=z(Qw);return e!=null&&!n.headers.has(r)&&(n=n.clone({headers:n.headers.set(r,e)})),t(n)}var Jd=(function(n){return n[n.Interceptors=0]="Interceptors",n[n.LegacyInterceptors=1]="LegacyInterceptors",n[n.CustomXsrfConfiguration=2]="CustomXsrfConfiguration",n[n.NoXsrfProtection=3]="NoXsrfProtection",n[n.JsonpSupport=4]="JsonpSupport",n[n.RequestsMadeViaParent=5]="RequestsMadeViaParent",n[n.Fetch=6]="Fetch",n})(Jd||{});function tb(n,t){return{ɵkind:n,ɵproviders:t}}function eb(...n){const t=[Qd,cs,{provide:qd,useExisting:cs},{provide:Xd,useFactory:()=>z(Uw,{optional:!0})??z(Yd)},{provide:Ia,useValue:nb,multi:!0}];for(const e of n)t.push(...e.ɵproviders);return qp(t)}function rb(n){return tb(Jd.Interceptors,n.map(t=>({provide:Ia,useValue:t,multi:!0})))}const fe=class fe{constructor(){}log(t){console.log(new Date().toLocaleDateString()+" "+new Date().toLocaleTimeString()+": "+t)}};fe.ɵfac=function(e){return new(e||fe)},fe.ɵprov=fn({token:fe,factory:fe.ɵfac,providedIn:"root"});let fs=fe;const de=class de{constructor(){this.logService=z(fs),this.tasks=jt([{id:1,title:"Task 1",description:"Description of Task 1"},{id:2,title:"Task 2",description:"Description of Task 2"},{id:3,title:"Task 3",description:"Description of Task 3"}]),this.isLoading=jt(!1),this.error=jt(""),this.action=jt(""),this.posts=jt([])}getTasks(){return this.tasks()}addTask(t){const e={id:Date.now(),...t};this.tasks.update(r=>[...r,e]),this.logService.log(`Added task: ${e.title}`)}updateTask(t,e){this.tasks.update(r=>r.map(i=>i.id===t?{...i,...e}:i)),this.logService.log(`Updated task with id: ${t}`)}deleteTask(t){this.tasks.update(e=>e.filter(r=>r.id!==t)),this.logService.log(`Deleted task with id: ${t}`)}};de.ɵfac=function(e){return new(e||de)},de.ɵprov=fn({token:de,factory:de.ɵfac,providedIn:"root"});let bi=de;const Kd=[{label:"Option 1",value:"option1"},{label:"Option 2",value:"option2"},{label:"Option 3",value:"option3"}],Nu=new on("OPTIONS",{providedIn:null,factory:()=>Kd});function ib(n){return new Promise(t=>setTimeout(t,n))}function ob(n,t){n&1&&(ca(0,"div",0)(1,"div",1),Cd(2,"div",2),fa()())}const Fe=class Fe{constructor(){this.storeService=z(bi)}};Fe.ɵfac=function(e){return new(e||Fe)},Fe.ɵcmp=vd({type:Fe,selectors:[["app-loading-overlay"]],decls:1,vars:1,consts:[[1,"loading-overlay"],[1,"loading-spinner"],[1,"spinner"]],template:function(e,r){e&1&&Jo(0,ob,3,0,"div",0),e&2&&Ko(r.storeService.isLoading()?0:-1)},dependencies:[uw],styles:[`.loading-overlay[_ngcontent-%COMP%] {\r
    position: fixed;\r
    inset: 0;\r
    z-index: 9999;\r
    background-color: rgba(0, 0, 0, 0.45);\r
    display: flex;\r
    align-items: center;\r
    justify-content: center;\r
    pointer-events: all;\r
    cursor: not-allowed;\r
    user-select: none;\r
}\r
\r
.loading-spinner[_ngcontent-%COMP%] {\r
    display: flex;\r
    align-items: center;\r
    justify-content: center;\r
}\r
\r
.spinner[_ngcontent-%COMP%] {\r
    width: 56px;\r
    height: 56px;\r
    border: 6px solid rgba(255, 255, 255, 0.25);\r
    border-top-color: #ffffff;\r
    border-radius: 50%;\r
    animation: _ngcontent-%COMP%_spin 0.75s linear infinite;\r
    cursor: default;\r
}\r
\r
@keyframes _ngcontent-%COMP%_spin {\r
    to {\r
        transform: rotate(360deg);\r
    }\r
}`]});let ds=Fe;const sb=(n,t)=>t.value,ab=(n,t)=>t.id;function lb(n,t){if(n&1&&(dn(0,"tr")(1,"td"),An(2),un(),dn(3,"td"),An(4),un(),dn(5,"td"),An(6),un()()),n&2){const e=t.$implicit;Nn(2),bt(e.id),Nn(2),bt(e.title),Nn(2),bt(e.description)}}function ub(n,t){if(n&1&&(dn(0,"option",6),An(1),un()),n&2){const e=t.$implicit;la("value",e.value),Nn(),bt(e.label)}}function cb(n,t){if(n&1&&(dn(0,"tr")(1,"td"),An(2),un(),dn(3,"td"),An(4),un(),dn(5,"td"),An(6),un(),dn(7,"td"),An(8),un()()),n&2){const e=t.$implicit;Nn(2),bt(e.userId),Nn(2),bt(e.id),Nn(2),bt(e.title),Nn(2),bt(e.body)}}function fb(n,t){if(n&1&&(dn(0,"div",9)(1,"div",10),ua(2,"div",11),un(),dn(3,"span",12),An(4),un()()),n&2){const e=xd();Nn(2),Td("width",e.downloadProgress(),"%"),Nn(2),ha("",e.downloadProgress(),"%")}}function db(n,t){if(n&1&&(dn(0,"div",9)(1,"span"),An(2),un()()),n&2){const e=xd();Nn(),da("my-error-text",e.downloadStatus().startsWith("Error")),Nn(),bt(e.downloadStatus())}}const Re=class Re{constructor(){this.storeService=z(bi),this.options=z(Nu),this.httpClient=z(Qd),this.destroyRef=z(Bs),this.postsShow=jt([]),this.appMessage=jt("Hello from App2Component!"),this.downloadProgress=jt(null),this.downloadStatus=jt("")}async fetchPosts(){this.storeService.isLoading.set(!0),this.appMessage.set("Fetching posts from API..."),await ib(2e3);const t=this.httpClient.get("https://jsonplaceholder.typicode.com/posts").subscribe({next:e=>{console.log("Fetching posts from API..."),console.log("Data received from API:",e),this.storeService.posts.set(e),console.log("Posts fetched successfully:",this.storeService.posts()),this.postsShow.set(this.storeService.posts().slice(0,10)),this.appMessage.set("Success: Posts fetched successfully!")},error:e=>{console.error("Error fetching posts:",e),this.storeService.isLoading.set(!1),this.appMessage.set("Error: Failed to fetch posts. Please try again later.")},complete:()=>{console.log("Fetch posts request completed."),this.storeService.isLoading.set(!1)}});this.destroyRef.onDestroy(()=>t.unsubscribe())}downloadFile(){const t="http://localhost:8080/test-api/file/download",e=new XMLHttpRequest;e.open("GET",t,!0),e.responseType="blob",this.downloadProgress.set(0),this.downloadStatus.set("Downloading..."),e.onprogress=r=>{if(r.lengthComputable){const i=Math.round(r.loaded/r.total*100);this.downloadProgress.set(i)}},e.onload=()=>{if(e.status===200){const r=e.response,i=document.createElement("a"),o=URL.createObjectURL(r);i.href=o,i.download="100MB.bin",i.click(),URL.revokeObjectURL(o),this.downloadProgress.set(100),this.downloadStatus.set("Download complete!")}else this.downloadStatus.set("Error: Download failed."),this.downloadProgress.set(null)},e.onerror=()=>{this.downloadStatus.set("Error: Network error during download."),this.downloadProgress.set(null)},e.send()}async ngOnInit(){}};Re.ɵfac=function(e){return new(e||Re)},Re.ɵcmp=vd({type:Re,selectors:[["app-root-2"]],features:[U1([{provide:Nu,useValue:Kd}])],decls:59,vars:6,consts:[[1,"my-container"],[1,"primary"],[3,"click"],[1,"ml-3","success",3,"click"],[1,"ml-3","error",3,"click"],["value",""],[3,"value"],[1,"mt-5","mb-5"],[3,"click","disabled"],[1,"mt-5"],[1,"progress-bar-container"],[1,"progress-bar-fill"],[1,"progress-label"]],template:function(e,r){e&1&&(dn(0,"div",0)(1,"div")(2,"table",1)(3,"thead")(4,"tr")(5,"th"),An(6,"Task id"),un(),dn(7,"th"),An(8,"Task title"),un(),dn(9,"th"),An(10,"Task description"),un()()(),dn(11,"tbody"),ro(12,lb,7,3,"tr",null,o1),un()()(),dn(14,"div")(15,"button",2),ke("click",function(){return r.storeService.addTask({title:"Task "+(r.storeService.getTasks().length+1),description:"New Task"})}),An(16,"Add Task"),un(),dn(17,"button",3),ke("click",function(){return r.storeService.updateTask(1,{title:"Task "+r.storeService.getTasks().length,description:"Updated Task"})}),An(18,"Update Task"),un(),dn(19,"button",4),ke("click",function(){return r.storeService.deleteTask(2)}),An(20,"Delete Task"),un()(),dn(21,"div")(22,"select")(23,"option",5),An(24,"Select an option"),un(),ro(25,ub,2,2,"option",6,sb),un()(),dn(27,"div")(28,"h1"),An(29,"HTTP Request Test"),un(),dn(30,"div")(31,"button",2),ke("click",function(){return r.fetchPosts()}),An(32,"Fetch data"),un()(),dn(33,"div",7)(34,"span"),An(35),un()(),dn(36,"table",1)(37,"thead")(38,"tr")(39,"th"),An(40,"User Id"),un(),dn(41,"th"),An(42,"Id"),un(),dn(43,"th"),An(44,"Title"),un(),dn(45,"th"),An(46,"Body"),un()()(),dn(47,"tbody"),ro(48,cb,9,4,"tr",null,ab),un()()(),dn(50,"div")(51,"h1"),An(52,"File download test"),un(),dn(53,"div")(54,"button",8),ke("click",function(){return r.downloadFile()}),An(55," Download File (100MB) "),un()(),Jo(56,fb,5,3,"div",9),Jo(57,db,3,3,"div",9),un()(),ua(58,"app-loading-overlay")),e&2&&(Nn(12),io(r.storeService.getTasks()),Nn(13),io(r.options),Nn(9),da("my-error-text",r.appMessage().startsWith("Error")),Nn(),bt(r.appMessage()),Nn(13),io(r.postsShow()),Nn(6),la("disabled",r.downloadProgress()!==null&&r.downloadProgress()<100),Nn(2),Ko(r.downloadProgress()!==null?56:-1),Nn(),Ko(r.downloadStatus()?57:-1))},dependencies:[ds],styles:[`html {\r
    font-family: sans-serif;\r
    -ms-text-size-adjust: 100%;\r
    -webkit-text-size-adjust: 100%\r
}\r
\r
body {\r
    margin: 0\r
}\r
\r
article,\r
aside,\r
details,\r
figcaption,\r
figure,\r
footer,\r
header,\r
hgroup,\r
main,\r
nav,\r
section,\r
summary {\r
    display: block\r
}\r
\r
audio,\r
canvas,\r
progress,\r
video {\r
    display: inline-block;\r
    vertical-align: baseline\r
}\r
\r
audio:not([controls]) {\r
    display: none;\r
    height: 0\r
}\r
\r
[hidden],\r
template {\r
    display: none\r
}\r
\r
a {\r
    background: transparent\r
}\r
\r
a:active,\r
a:hover {\r
    outline: 0\r
}\r
\r
abbr[title] {\r
    border-bottom: 1px dotted\r
}\r
\r
b,\r
strong {\r
    font-weight: bold\r
}\r
\r
dfn {\r
    font-style: italic\r
}\r
\r
h1 {\r
    font-size: 2em;\r
    margin: .67em 0\r
}\r
\r
mark {\r
    background: #fff;\r
    color: #111\r
}\r
\r
small {\r
    font-size: 80%\r
}\r
\r
sub,\r
sup {\r
    font-size: 75%;\r
    line-height: 0;\r
    position: relative;\r
    vertical-align: baseline\r
}\r
\r
sup {\r
    top: -0.5em\r
}\r
\r
sub {\r
    bottom: -0.25em\r
}\r
\r
img {\r
    border: 0\r
}\r
\r
svg:not(:root) {\r
    overflow: hidden\r
}\r
\r
figure {\r
    margin: 1em 40px\r
}\r
\r
hr {\r
    -moz-box-sizing: content-box;\r
    box-sizing: content-box;\r
    height: 0\r
}\r
\r
pre {\r
    overflow: auto\r
}\r
\r
code,\r
kbd,\r
pre,\r
samp {\r
    font-family: monospace, monospace;\r
    font-size: 1em\r
}\r
\r
button,\r
input,\r
optgroup,\r
select,\r
textarea {\r
    color: inherit;\r
    font: inherit;\r
    margin: 0\r
}\r
\r
button {\r
    overflow: visible\r
}\r
\r
button,\r
select {\r
    text-transform: none\r
}\r
\r
button,\r
html input[type=button],\r
input[type=reset],\r
input[type=submit] {\r
    -webkit-appearance: button;\r
    cursor: pointer\r
}\r
\r
button[disabled],\r
input[disabled] {\r
    cursor: default\r
}\r
\r
button::-moz-focus-inner,\r
input::-moz-focus-inner {\r
    border: 0;\r
    padding: 0\r
}\r
\r
input {\r
    line-height: normal\r
}\r
\r
input[type=checkbox],\r
input[type=radio] {\r
    box-sizing: border-box;\r
    padding: 0\r
}\r
\r
input[type=number]::-webkit-inner-spin-button,\r
input[type=number]::-webkit-outer-spin-button {\r
    height: auto\r
}\r
\r
input[type=search]::-webkit-search-cancel-button,\r
input[type=search]::-webkit-search-decoration {\r
    -webkit-appearance: none\r
}\r
\r
fieldset {\r
    border: 0;\r
    padding: 0\r
}\r
\r
legend {\r
    border: 0;\r
    padding: 0\r
}\r
\r
textarea {\r
    overflow: auto\r
}\r
\r
optgroup {\r
    font-weight: bold\r
}\r
\r
table {\r
    border-collapse: collapse;\r
    border-spacing: 0\r
}\r
\r
td,\r
th {\r
    padding: 0\r
}\r
\r
* {\r
    box-sizing: inherit\r
}\r
\r
html,\r
body {\r
    font-family: Arial, Helvetica, sans-serif;\r
    box-sizing: border-box;\r
    height: 100%\r
}\r
\r
body {\r
    color: #111;\r
    font-size: 1.1em;\r
    line-height: 1.5;\r
    background: #fff\r
}\r
\r
main {\r
    display: block\r
}\r
\r
h1,\r
h2,\r
h3,\r
h4,\r
h5,\r
h6 {\r
    margin: 0;\r
    padding: .6em 0\r
}\r
\r
li {\r
    margin: 0 0 .3em\r
}\r
\r
a {\r
    color: #0074d9;\r
    text-decoration: none;\r
    box-shadow: none;\r
    transition: all .3s\r
}\r
\r
code {\r
    padding: .3em .6em;\r
    font-size: .8em;\r
    background: #f5f5f5\r
}\r
\r
pre {\r
    text-align: left;\r
    padding: .3em;\r
    background: #f5f5f5;\r
    border-radius: .2em\r
}\r
\r
pre code {\r
    padding: 0\r
}\r
\r
blockquote {\r
    padding: 0 0 0 1em;\r
    margin: 0 0 0 .1em;\r
    box-shadow: inset 5px 0 rgba(17, 17, 17, .3)\r
}\r
\r
label {\r
    cursor: pointer\r
}\r
\r
[class^=icon-]:before,\r
[class*=" icon-"]:before {\r
    margin: 0 .6em 0 0\r
}\r
\r
i[class^=icon-]:before,\r
i[class*=" icon-"]:before {\r
    margin: 0\r
}\r
\r
.dropimage,\r
button,\r
.button,\r
[type=submit],\r
.label,\r
[data-tooltip]:after {\r
    display: inline-block;\r
    text-align: center;\r
    letter-spacing: inherit;\r
    margin: 0;\r
    padding: .3em .9em;\r
    vertical-align: middle;\r
    background: #0074d9;\r
    color: #fff;\r
    border: 0;\r
    border-radius: .2em;\r
    width: auto;\r
    -webkit-touch-callout: none;\r
    -webkit-user-select: none;\r
    -khtml-user-select: none;\r
    -moz-user-select: none;\r
    -ms-user-select: none;\r
    user-select: none\r
}\r
\r
.success.dropimage,\r
button.success,\r
.success.button,\r
.success[type=submit],\r
.success.label,\r
.success[data-tooltip]:after {\r
    background: #2ecc40\r
}\r
\r
.warning.dropimage,\r
button.warning,\r
.warning.button,\r
.warning[type=submit],\r
.warning.label,\r
.warning[data-tooltip]:after {\r
    background: #ff851b\r
}\r
\r
.error.dropimage,\r
button.error,\r
.error.button,\r
.error[type=submit],\r
.error.label,\r
.error[data-tooltip]:after {\r
    background: #ff4136\r
}\r
\r
.pseudo.dropimage,\r
button.pseudo,\r
.pseudo.button,\r
.pseudo[type=submit],\r
.pseudo.label,\r
.pseudo[data-tooltip]:after {\r
    background-color: transparent;\r
    color: inherit\r
}\r
\r
.label,\r
[data-tooltip]:after {\r
    font-size: .6em;\r
    padding: .4em .6em;\r
    margin-left: 1em;\r
    line-height: 1\r
}\r
\r
.dropimage,\r
button,\r
.button,\r
[type=submit] {\r
    margin: .3em 0;\r
    cursor: pointer;\r
    transition: all .3s;\r
    border-radius: .2em;\r
    height: auto;\r
    vertical-align: baseline;\r
    box-shadow: 0 0 rgba(17, 17, 17, 0) inset\r
}\r
\r
.dropimage:hover,\r
button:hover,\r
.button:hover,\r
[type=submit]:hover,\r
.dropimage:focus,\r
button:focus,\r
.button:focus,\r
[type=submit]:focus {\r
    box-shadow: inset 0 0 0 99em rgba(255, 255, 255, .2);\r
    border: 0\r
}\r
\r
.pseudo.dropimage:hover,\r
button.pseudo:hover,\r
.pseudo.button:hover,\r
.pseudo[type=submit]:hover,\r
.pseudo.dropimage:focus,\r
button.pseudo:focus,\r
.pseudo.button:focus,\r
.pseudo[type=submit]:focus {\r
    box-shadow: inset 0 0 0 99em rgba(17, 17, 17, .1)\r
}\r
\r
.active.dropimage,\r
button.active,\r
.active.button,\r
.active[type=submit],\r
.dropimage:active,\r
button:active,\r
.button:active,\r
[type=submit]:active {\r
    box-shadow: inset 0 0 0 99em rgba(17, 17, 17, .2)\r
}\r
\r
[disabled].dropimage,\r
button[disabled],\r
[disabled].button,\r
[disabled][type=submit] {\r
    cursor: default;\r
    box-shadow: none;\r
    background: #aaa\r
}\r
\r
:checked+.toggle,\r
:checked+.toggle:hover {\r
    box-shadow: inset 0 0 0 99em rgba(17, 17, 17, .2)\r
}\r
\r
[type]+.toggle {\r
    padding: .3em .9em;\r
    margin-right: 0\r
}\r
\r
[type]+.toggle:after,\r
[type]+.toggle:before {\r
    display: none\r
}\r
\r
input,\r
textarea,\r
.select select {\r
    line-height: 1.5;\r
    margin: 0;\r
    height: 2.1em;\r
    padding: .3em .6em;\r
    border: 1px solid #aaa;\r
    background-color: #fff;\r
    border-radius: .2em;\r
    transition: all .3s;\r
    width: 100%\r
}\r
\r
input:focus,\r
textarea:focus,\r
.select select:focus {\r
    border: 1px solid #0074d9;\r
    outline: 0\r
}\r
\r
textarea {\r
    height: auto\r
}\r
\r
[type=file],\r
[type=color] {\r
    cursor: pointer\r
}\r
\r
[type=file] {\r
    height: auto\r
}\r
\r
select {\r
    background: #fff url(data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyIiBoZWlnaHQ9IjMiPjxwYXRoIGQ9Im0gMCwxIDEsMiAxLC0yIHoiLz48L3N2Zz4=) no-repeat scroll 95% center/10px 15px;\r
    background-position: calc(100% - 15px) center;\r
    border: 1px solid #aaa;\r
    border-radius: .2em;\r
    cursor: pointer;\r
    width: 100%;\r
    height: 2.2em;\r
    box-sizing: border-box;\r
    padding: .3em .45em;\r
    transition: all .3s;\r
    -moz-appearance: none;\r
    -webkit-appearance: none;\r
    appearance: none\r
}\r
\r
select::-ms-expand {\r
    display: none\r
}\r
\r
select:focus,\r
select:active {\r
    border: 1px solid #0074d9;\r
    transition: outline 0s\r
}\r
\r
select:-moz-focusring {\r
    color: transparent;\r
    text-shadow: 0 0 0 #111\r
}\r
\r
select option {\r
    font-size: inherit;\r
    padding: .45em\r
}\r
\r
select[multiple] {\r
    height: auto;\r
    background: none;\r
    padding: 0\r
}\r
\r
[type=checkbox],\r
[type=radio] {\r
    opacity: 0;\r
    width: 0;\r
    position: absolute;\r
    display: inline-block\r
}\r
\r
[type=checkbox]+.checkable:hover:before,\r
[type=radio]+.checkable:hover:before,\r
[type=checkbox]:focus+.checkable:before,\r
[type=radio]:focus+.checkable:before {\r
    border: 1px solid #0074d9\r
}\r
\r
[type=checkbox]+.checkable,\r
[type=radio]+.checkable {\r
    position: relative;\r
    cursor: pointer;\r
    padding-left: 1.5em;\r
    margin-right: .6em\r
}\r
\r
[type=checkbox]+.checkable:before,\r
[type=radio]+.checkable:before,\r
[type=checkbox]+.checkable:after,\r
[type=radio]+.checkable:after {\r
    content: "";\r
    position: absolute;\r
    display: inline-block;\r
    left: 0;\r
    top: 50%;\r
    transform: translateY(-50%);\r
    font-size: 1em;\r
    line-height: 1em;\r
    color: transparent;\r
    font-family: sans;\r
    text-align: center;\r
    box-sizing: border-box;\r
    width: 1em;\r
    height: 1em;\r
    border-radius: 50%;\r
    transition: all .3s\r
}\r
\r
[type=checkbox]+.checkable:before,\r
[type=radio]+.checkable:before {\r
    border: 1px solid #aaa\r
}\r
\r
[type=checkbox]:checked+.checkable:after,\r
[type=radio]:checked+.checkable:after {\r
    background: #111;\r
    transform: scale(0.5) translateY(-100%)\r
}\r
\r
[type=checkbox]+.checkable:before {\r
    border-radius: .2em\r
}\r
\r
[type=checkbox]+.checkable:after {\r
    content: "✔";\r
    background: none;\r
    transform: scale(2) translateY(-25%);\r
    visibility: hidden;\r
    opacity: 0\r
}\r
\r
[type=checkbox]:checked+.checkable:after {\r
    color: #111;\r
    background: none;\r
    transform: translateY(-50%);\r
    transition: all .3s;\r
    visibility: visible;\r
    opacity: 1\r
}\r
\r
table {\r
    text-align: left\r
}\r
\r
td,\r
th {\r
    padding: .3em 2.4em .3em .6em\r
}\r
\r
th {\r
    text-align: left;\r
    font-weight: 900;\r
    color: #fff;\r
    background-color: #0074d9\r
}\r
\r
.success th {\r
    background-color: #2ecc40\r
}\r
\r
.warning th {\r
    background-color: #ff851b\r
}\r
\r
.error th {\r
    background-color: #ff4136\r
}\r
\r
.dull th {\r
    background-color: #aaa\r
}\r
\r
tr:nth-child(even) {\r
    background: rgba(17, 17, 17, .05)\r
}\r
\r
.flex {\r
    display: -ms-flexbox;\r
    display: flex;\r
    margin-left: -0.6em;\r
    width: calc(100% + 0.6em);\r
    flex-wrap: wrap;\r
    transition: all .3s ease\r
}\r
\r
.flex>* {\r
    box-sizing: border-box;\r
    flex: 1 1 auto;\r
    padding-left: .6em;\r
    padding-bottom: .6em\r
}\r
\r
.flex[class*=one]>*,\r
.flex[class*=two]>*,\r
.flex[class*=three]>*,\r
.flex[class*=four]>*,\r
.flex[class*=five]>*,\r
.flex[class*=six]>*,\r
.flex[class*=seven]>*,\r
.flex[class*=eight]>*,\r
.flex[class*=nine]>*,\r
.flex[class*=ten]>*,\r
.flex[class*=eleven]>*,\r
.flex[class*=twelve]>* {\r
    flex-grow: 0\r
}\r
\r
.flex.grow>* {\r
    flex-grow: 1\r
}\r
\r
.center {\r
    justify-content: center\r
}\r
\r
.one>* {\r
    width: 100%\r
}\r
\r
.two>* {\r
    width: 50%\r
}\r
\r
.three>* {\r
    width: 33.33333%\r
}\r
\r
.four>* {\r
    width: 25%\r
}\r
\r
.five>* {\r
    width: 20%\r
}\r
\r
.six>* {\r
    width: 16.66666%\r
}\r
\r
.seven>* {\r
    width: 14.28571%\r
}\r
\r
.eight>* {\r
    width: 12.5%\r
}\r
\r
.nine>* {\r
    width: 11.11111%\r
}\r
\r
.ten>* {\r
    width: 10%\r
}\r
\r
.eleven>* {\r
    width: 9.09091%\r
}\r
\r
.twelve>* {\r
    width: 8.33333%\r
}\r
\r
@media all and (min-width: 500px) {\r
    .one-500>* {\r
        width: 100%\r
    }\r
\r
    .two-500>* {\r
        width: 50%\r
    }\r
\r
    .three-500>* {\r
        width: 33.33333%\r
    }\r
\r
    .four-500>* {\r
        width: 25%\r
    }\r
\r
    .five-500>* {\r
        width: 20%\r
    }\r
\r
    .six-500>* {\r
        width: 16.66666%\r
    }\r
\r
    .seven-500>* {\r
        width: 14.28571%\r
    }\r
\r
    .eight-500>* {\r
        width: 12.5%\r
    }\r
\r
    .nine-500>* {\r
        width: 11.11111%\r
    }\r
\r
    .ten-500>* {\r
        width: 10%\r
    }\r
\r
    .eleven-500>* {\r
        width: 9.09091%\r
    }\r
\r
    .twelve-500>* {\r
        width: 8.33333%\r
    }\r
}\r
\r
@media all and (min-width: 600px) {\r
    .one-600>* {\r
        width: 100%\r
    }\r
\r
    .two-600>* {\r
        width: 50%\r
    }\r
\r
    .three-600>* {\r
        width: 33.33333%\r
    }\r
\r
    .four-600>* {\r
        width: 25%\r
    }\r
\r
    .five-600>* {\r
        width: 20%\r
    }\r
\r
    .six-600>* {\r
        width: 16.66666%\r
    }\r
\r
    .seven-600>* {\r
        width: 14.28571%\r
    }\r
\r
    .eight-600>* {\r
        width: 12.5%\r
    }\r
\r
    .nine-600>* {\r
        width: 11.11111%\r
    }\r
\r
    .ten-600>* {\r
        width: 10%\r
    }\r
\r
    .eleven-600>* {\r
        width: 9.09091%\r
    }\r
\r
    .twelve-600>* {\r
        width: 8.33333%\r
    }\r
}\r
\r
@media all and (min-width: 700px) {\r
    .one-700>* {\r
        width: 100%\r
    }\r
\r
    .two-700>* {\r
        width: 50%\r
    }\r
\r
    .three-700>* {\r
        width: 33.33333%\r
    }\r
\r
    .four-700>* {\r
        width: 25%\r
    }\r
\r
    .five-700>* {\r
        width: 20%\r
    }\r
\r
    .six-700>* {\r
        width: 16.66666%\r
    }\r
\r
    .seven-700>* {\r
        width: 14.28571%\r
    }\r
\r
    .eight-700>* {\r
        width: 12.5%\r
    }\r
\r
    .nine-700>* {\r
        width: 11.11111%\r
    }\r
\r
    .ten-700>* {\r
        width: 10%\r
    }\r
\r
    .eleven-700>* {\r
        width: 9.09091%\r
    }\r
\r
    .twelve-700>* {\r
        width: 8.33333%\r
    }\r
}\r
\r
@media all and (min-width: 800px) {\r
    .one-800>* {\r
        width: 100%\r
    }\r
\r
    .two-800>* {\r
        width: 50%\r
    }\r
\r
    .three-800>* {\r
        width: 33.33333%\r
    }\r
\r
    .four-800>* {\r
        width: 25%\r
    }\r
\r
    .five-800>* {\r
        width: 20%\r
    }\r
\r
    .six-800>* {\r
        width: 16.66666%\r
    }\r
\r
    .seven-800>* {\r
        width: 14.28571%\r
    }\r
\r
    .eight-800>* {\r
        width: 12.5%\r
    }\r
\r
    .nine-800>* {\r
        width: 11.11111%\r
    }\r
\r
    .ten-800>* {\r
        width: 10%\r
    }\r
\r
    .eleven-800>* {\r
        width: 9.09091%\r
    }\r
\r
    .twelve-800>* {\r
        width: 8.33333%\r
    }\r
}\r
\r
@media all and (min-width: 900px) {\r
    .one-900>* {\r
        width: 100%\r
    }\r
\r
    .two-900>* {\r
        width: 50%\r
    }\r
\r
    .three-900>* {\r
        width: 33.33333%\r
    }\r
\r
    .four-900>* {\r
        width: 25%\r
    }\r
\r
    .five-900>* {\r
        width: 20%\r
    }\r
\r
    .six-900>* {\r
        width: 16.66666%\r
    }\r
\r
    .seven-900>* {\r
        width: 14.28571%\r
    }\r
\r
    .eight-900>* {\r
        width: 12.5%\r
    }\r
\r
    .nine-900>* {\r
        width: 11.11111%\r
    }\r
\r
    .ten-900>* {\r
        width: 10%\r
    }\r
\r
    .eleven-900>* {\r
        width: 9.09091%\r
    }\r
\r
    .twelve-900>* {\r
        width: 8.33333%\r
    }\r
}\r
\r
@media all and (min-width: 1000px) {\r
    .one-1000>* {\r
        width: 100%\r
    }\r
\r
    .two-1000>* {\r
        width: 50%\r
    }\r
\r
    .three-1000>* {\r
        width: 33.33333%\r
    }\r
\r
    .four-1000>* {\r
        width: 25%\r
    }\r
\r
    .five-1000>* {\r
        width: 20%\r
    }\r
\r
    .six-1000>* {\r
        width: 16.66666%\r
    }\r
\r
    .seven-1000>* {\r
        width: 14.28571%\r
    }\r
\r
    .eight-1000>* {\r
        width: 12.5%\r
    }\r
\r
    .nine-1000>* {\r
        width: 11.11111%\r
    }\r
\r
    .ten-1000>* {\r
        width: 10%\r
    }\r
\r
    .eleven-1000>* {\r
        width: 9.09091%\r
    }\r
\r
    .twelve-1000>* {\r
        width: 8.33333%\r
    }\r
}\r
\r
@media all and (min-width: 1100px) {\r
    .one-1100>* {\r
        width: 100%\r
    }\r
\r
    .two-1100>* {\r
        width: 50%\r
    }\r
\r
    .three-1100>* {\r
        width: 33.33333%\r
    }\r
\r
    .four-1100>* {\r
        width: 25%\r
    }\r
\r
    .five-1100>* {\r
        width: 20%\r
    }\r
\r
    .six-1100>* {\r
        width: 16.66666%\r
    }\r
\r
    .seven-1100>* {\r
        width: 14.28571%\r
    }\r
\r
    .eight-1100>* {\r
        width: 12.5%\r
    }\r
\r
    .nine-1100>* {\r
        width: 11.11111%\r
    }\r
\r
    .ten-1100>* {\r
        width: 10%\r
    }\r
\r
    .eleven-1100>* {\r
        width: 9.09091%\r
    }\r
\r
    .twelve-1100>* {\r
        width: 8.33333%\r
    }\r
}\r
\r
@media all and (min-width: 1200px) {\r
    .one-1200>* {\r
        width: 100%\r
    }\r
\r
    .two-1200>* {\r
        width: 50%\r
    }\r
\r
    .three-1200>* {\r
        width: 33.33333%\r
    }\r
\r
    .four-1200>* {\r
        width: 25%\r
    }\r
\r
    .five-1200>* {\r
        width: 20%\r
    }\r
\r
    .six-1200>* {\r
        width: 16.66666%\r
    }\r
\r
    .seven-1200>* {\r
        width: 14.28571%\r
    }\r
\r
    .eight-1200>* {\r
        width: 12.5%\r
    }\r
\r
    .nine-1200>* {\r
        width: 11.11111%\r
    }\r
\r
    .ten-1200>* {\r
        width: 10%\r
    }\r
\r
    .eleven-1200>* {\r
        width: 9.09091%\r
    }\r
\r
    .twelve-1200>* {\r
        width: 8.33333%\r
    }\r
}\r
\r
@media all and (min-width: 1300px) {\r
    .one-1300>* {\r
        width: 100%\r
    }\r
\r
    .two-1300>* {\r
        width: 50%\r
    }\r
\r
    .three-1300>* {\r
        width: 33.33333%\r
    }\r
\r
    .four-1300>* {\r
        width: 25%\r
    }\r
\r
    .five-1300>* {\r
        width: 20%\r
    }\r
\r
    .six-1300>* {\r
        width: 16.66666%\r
    }\r
\r
    .seven-1300>* {\r
        width: 14.28571%\r
    }\r
\r
    .eight-1300>* {\r
        width: 12.5%\r
    }\r
\r
    .nine-1300>* {\r
        width: 11.11111%\r
    }\r
\r
    .ten-1300>* {\r
        width: 10%\r
    }\r
\r
    .eleven-1300>* {\r
        width: 9.09091%\r
    }\r
\r
    .twelve-1300>* {\r
        width: 8.33333%\r
    }\r
}\r
\r
@media all and (min-width: 1400px) {\r
    .one-1400>* {\r
        width: 100%\r
    }\r
\r
    .two-1400>* {\r
        width: 50%\r
    }\r
\r
    .three-1400>* {\r
        width: 33.33333%\r
    }\r
\r
    .four-1400>* {\r
        width: 25%\r
    }\r
\r
    .five-1400>* {\r
        width: 20%\r
    }\r
\r
    .six-1400>* {\r
        width: 16.66666%\r
    }\r
\r
    .seven-1400>* {\r
        width: 14.28571%\r
    }\r
\r
    .eight-1400>* {\r
        width: 12.5%\r
    }\r
\r
    .nine-1400>* {\r
        width: 11.11111%\r
    }\r
\r
    .ten-1400>* {\r
        width: 10%\r
    }\r
\r
    .eleven-1400>* {\r
        width: 9.09091%\r
    }\r
\r
    .twelve-1400>* {\r
        width: 8.33333%\r
    }\r
}\r
\r
@media all and (min-width: 1500px) {\r
    .one-1500>* {\r
        width: 100%\r
    }\r
\r
    .two-1500>* {\r
        width: 50%\r
    }\r
\r
    .three-1500>* {\r
        width: 33.33333%\r
    }\r
\r
    .four-1500>* {\r
        width: 25%\r
    }\r
\r
    .five-1500>* {\r
        width: 20%\r
    }\r
\r
    .six-1500>* {\r
        width: 16.66666%\r
    }\r
\r
    .seven-1500>* {\r
        width: 14.28571%\r
    }\r
\r
    .eight-1500>* {\r
        width: 12.5%\r
    }\r
\r
    .nine-1500>* {\r
        width: 11.11111%\r
    }\r
\r
    .ten-1500>* {\r
        width: 10%\r
    }\r
\r
    .eleven-1500>* {\r
        width: 9.09091%\r
    }\r
\r
    .twelve-1500>* {\r
        width: 8.33333%\r
    }\r
}\r
\r
@media all and (min-width: 1600px) {\r
    .one-1600>* {\r
        width: 100%\r
    }\r
\r
    .two-1600>* {\r
        width: 50%\r
    }\r
\r
    .three-1600>* {\r
        width: 33.33333%\r
    }\r
\r
    .four-1600>* {\r
        width: 25%\r
    }\r
\r
    .five-1600>* {\r
        width: 20%\r
    }\r
\r
    .six-1600>* {\r
        width: 16.66666%\r
    }\r
\r
    .seven-1600>* {\r
        width: 14.28571%\r
    }\r
\r
    .eight-1600>* {\r
        width: 12.5%\r
    }\r
\r
    .nine-1600>* {\r
        width: 11.11111%\r
    }\r
\r
    .ten-1600>* {\r
        width: 10%\r
    }\r
\r
    .eleven-1600>* {\r
        width: 9.09091%\r
    }\r
\r
    .twelve-1600>* {\r
        width: 8.33333%\r
    }\r
}\r
\r
@media all and (min-width: 1700px) {\r
    .one-1700>* {\r
        width: 100%\r
    }\r
\r
    .two-1700>* {\r
        width: 50%\r
    }\r
\r
    .three-1700>* {\r
        width: 33.33333%\r
    }\r
\r
    .four-1700>* {\r
        width: 25%\r
    }\r
\r
    .five-1700>* {\r
        width: 20%\r
    }\r
\r
    .six-1700>* {\r
        width: 16.66666%\r
    }\r
\r
    .seven-1700>* {\r
        width: 14.28571%\r
    }\r
\r
    .eight-1700>* {\r
        width: 12.5%\r
    }\r
\r
    .nine-1700>* {\r
        width: 11.11111%\r
    }\r
\r
    .ten-1700>* {\r
        width: 10%\r
    }\r
\r
    .eleven-1700>* {\r
        width: 9.09091%\r
    }\r
\r
    .twelve-1700>* {\r
        width: 8.33333%\r
    }\r
}\r
\r
@media all and (min-width: 1800px) {\r
    .one-1800>* {\r
        width: 100%\r
    }\r
\r
    .two-1800>* {\r
        width: 50%\r
    }\r
\r
    .three-1800>* {\r
        width: 33.33333%\r
    }\r
\r
    .four-1800>* {\r
        width: 25%\r
    }\r
\r
    .five-1800>* {\r
        width: 20%\r
    }\r
\r
    .six-1800>* {\r
        width: 16.66666%\r
    }\r
\r
    .seven-1800>* {\r
        width: 14.28571%\r
    }\r
\r
    .eight-1800>* {\r
        width: 12.5%\r
    }\r
\r
    .nine-1800>* {\r
        width: 11.11111%\r
    }\r
\r
    .ten-1800>* {\r
        width: 10%\r
    }\r
\r
    .eleven-1800>* {\r
        width: 9.09091%\r
    }\r
\r
    .twelve-1800>* {\r
        width: 8.33333%\r
    }\r
}\r
\r
@media all and (min-width: 1900px) {\r
    .one-1900>* {\r
        width: 100%\r
    }\r
\r
    .two-1900>* {\r
        width: 50%\r
    }\r
\r
    .three-1900>* {\r
        width: 33.33333%\r
    }\r
\r
    .four-1900>* {\r
        width: 25%\r
    }\r
\r
    .five-1900>* {\r
        width: 20%\r
    }\r
\r
    .six-1900>* {\r
        width: 16.66666%\r
    }\r
\r
    .seven-1900>* {\r
        width: 14.28571%\r
    }\r
\r
    .eight-1900>* {\r
        width: 12.5%\r
    }\r
\r
    .nine-1900>* {\r
        width: 11.11111%\r
    }\r
\r
    .ten-1900>* {\r
        width: 10%\r
    }\r
\r
    .eleven-1900>* {\r
        width: 9.09091%\r
    }\r
\r
    .twelve-1900>* {\r
        width: 8.33333%\r
    }\r
}\r
\r
@media all and (min-width: 2000px) {\r
    .one-2000>* {\r
        width: 100%\r
    }\r
\r
    .two-2000>* {\r
        width: 50%\r
    }\r
\r
    .three-2000>* {\r
        width: 33.33333%\r
    }\r
\r
    .four-2000>* {\r
        width: 25%\r
    }\r
\r
    .five-2000>* {\r
        width: 20%\r
    }\r
\r
    .six-2000>* {\r
        width: 16.66666%\r
    }\r
\r
    .seven-2000>* {\r
        width: 14.28571%\r
    }\r
\r
    .eight-2000>* {\r
        width: 12.5%\r
    }\r
\r
    .nine-2000>* {\r
        width: 11.11111%\r
    }\r
\r
    .ten-2000>* {\r
        width: 10%\r
    }\r
\r
    .eleven-2000>* {\r
        width: 9.09091%\r
    }\r
\r
    .twelve-2000>* {\r
        width: 8.33333%\r
    }\r
}\r
\r
.full {\r
    width: 100%\r
}\r
\r
.half {\r
    width: 50%\r
}\r
\r
.third {\r
    width: 33.33333%\r
}\r
\r
.two-third {\r
    width: 66.66666%\r
}\r
\r
.fourth {\r
    width: 25%\r
}\r
\r
.three-fourth {\r
    width: 75%\r
}\r
\r
.fifth {\r
    width: 20%\r
}\r
\r
.two-fifth {\r
    width: 40%\r
}\r
\r
.three-fifth {\r
    width: 60%\r
}\r
\r
.four-fifth {\r
    width: 80%\r
}\r
\r
.sixth {\r
    width: 16.66666%\r
}\r
\r
.none {\r
    display: none\r
}\r
\r
@media all and (min-width: 500px) {\r
    .full-500 {\r
        width: 100%;\r
        display: block\r
    }\r
\r
    .half-500 {\r
        width: 50%;\r
        display: block\r
    }\r
\r
    .third-500 {\r
        width: 33.33333%;\r
        display: block\r
    }\r
\r
    .two-third-500 {\r
        width: 66.66666%;\r
        display: block\r
    }\r
\r
    .fourth-500 {\r
        width: 25%;\r
        display: block\r
    }\r
\r
    .three-fourth-500 {\r
        width: 75%;\r
        display: block\r
    }\r
\r
    .fifth-500 {\r
        width: 20%;\r
        display: block\r
    }\r
\r
    .two-fifth-500 {\r
        width: 40%;\r
        display: block\r
    }\r
\r
    .three-fifth-500 {\r
        width: 60%;\r
        display: block\r
    }\r
\r
    .four-fifth-500 {\r
        width: 80%;\r
        display: block\r
    }\r
\r
    .sixth-500 {\r
        width: 16.66666%;\r
        display: block\r
    }\r
}\r
\r
@media all and (min-width: 600px) {\r
    .full-600 {\r
        width: 100%;\r
        display: block\r
    }\r
\r
    .half-600 {\r
        width: 50%;\r
        display: block\r
    }\r
\r
    .third-600 {\r
        width: 33.33333%;\r
        display: block\r
    }\r
\r
    .two-third-600 {\r
        width: 66.66666%;\r
        display: block\r
    }\r
\r
    .fourth-600 {\r
        width: 25%;\r
        display: block\r
    }\r
\r
    .three-fourth-600 {\r
        width: 75%;\r
        display: block\r
    }\r
\r
    .fifth-600 {\r
        width: 20%;\r
        display: block\r
    }\r
\r
    .two-fifth-600 {\r
        width: 40%;\r
        display: block\r
    }\r
\r
    .three-fifth-600 {\r
        width: 60%;\r
        display: block\r
    }\r
\r
    .four-fifth-600 {\r
        width: 80%;\r
        display: block\r
    }\r
\r
    .sixth-600 {\r
        width: 16.66666%;\r
        display: block\r
    }\r
}\r
\r
@media all and (min-width: 700px) {\r
    .full-700 {\r
        width: 100%;\r
        display: block\r
    }\r
\r
    .half-700 {\r
        width: 50%;\r
        display: block\r
    }\r
\r
    .third-700 {\r
        width: 33.33333%;\r
        display: block\r
    }\r
\r
    .two-third-700 {\r
        width: 66.66666%;\r
        display: block\r
    }\r
\r
    .fourth-700 {\r
        width: 25%;\r
        display: block\r
    }\r
\r
    .three-fourth-700 {\r
        width: 75%;\r
        display: block\r
    }\r
\r
    .fifth-700 {\r
        width: 20%;\r
        display: block\r
    }\r
\r
    .two-fifth-700 {\r
        width: 40%;\r
        display: block\r
    }\r
\r
    .three-fifth-700 {\r
        width: 60%;\r
        display: block\r
    }\r
\r
    .four-fifth-700 {\r
        width: 80%;\r
        display: block\r
    }\r
\r
    .sixth-700 {\r
        width: 16.66666%;\r
        display: block\r
    }\r
}\r
\r
@media all and (min-width: 800px) {\r
    .full-800 {\r
        width: 100%;\r
        display: block\r
    }\r
\r
    .half-800 {\r
        width: 50%;\r
        display: block\r
    }\r
\r
    .third-800 {\r
        width: 33.33333%;\r
        display: block\r
    }\r
\r
    .two-third-800 {\r
        width: 66.66666%;\r
        display: block\r
    }\r
\r
    .fourth-800 {\r
        width: 25%;\r
        display: block\r
    }\r
\r
    .three-fourth-800 {\r
        width: 75%;\r
        display: block\r
    }\r
\r
    .fifth-800 {\r
        width: 20%;\r
        display: block\r
    }\r
\r
    .two-fifth-800 {\r
        width: 40%;\r
        display: block\r
    }\r
\r
    .three-fifth-800 {\r
        width: 60%;\r
        display: block\r
    }\r
\r
    .four-fifth-800 {\r
        width: 80%;\r
        display: block\r
    }\r
\r
    .sixth-800 {\r
        width: 16.66666%;\r
        display: block\r
    }\r
}\r
\r
@media all and (min-width: 900px) {\r
    .full-900 {\r
        width: 100%;\r
        display: block\r
    }\r
\r
    .half-900 {\r
        width: 50%;\r
        display: block\r
    }\r
\r
    .third-900 {\r
        width: 33.33333%;\r
        display: block\r
    }\r
\r
    .two-third-900 {\r
        width: 66.66666%;\r
        display: block\r
    }\r
\r
    .fourth-900 {\r
        width: 25%;\r
        display: block\r
    }\r
\r
    .three-fourth-900 {\r
        width: 75%;\r
        display: block\r
    }\r
\r
    .fifth-900 {\r
        width: 20%;\r
        display: block\r
    }\r
\r
    .two-fifth-900 {\r
        width: 40%;\r
        display: block\r
    }\r
\r
    .three-fifth-900 {\r
        width: 60%;\r
        display: block\r
    }\r
\r
    .four-fifth-900 {\r
        width: 80%;\r
        display: block\r
    }\r
\r
    .sixth-900 {\r
        width: 16.66666%;\r
        display: block\r
    }\r
}\r
\r
@media all and (min-width: 1000px) {\r
    .full-1000 {\r
        width: 100%;\r
        display: block\r
    }\r
\r
    .half-1000 {\r
        width: 50%;\r
        display: block\r
    }\r
\r
    .third-1000 {\r
        width: 33.33333%;\r
        display: block\r
    }\r
\r
    .two-third-1000 {\r
        width: 66.66666%;\r
        display: block\r
    }\r
\r
    .fourth-1000 {\r
        width: 25%;\r
        display: block\r
    }\r
\r
    .three-fourth-1000 {\r
        width: 75%;\r
        display: block\r
    }\r
\r
    .fifth-1000 {\r
        width: 20%;\r
        display: block\r
    }\r
\r
    .two-fifth-1000 {\r
        width: 40%;\r
        display: block\r
    }\r
\r
    .three-fifth-1000 {\r
        width: 60%;\r
        display: block\r
    }\r
\r
    .four-fifth-1000 {\r
        width: 80%;\r
        display: block\r
    }\r
\r
    .sixth-1000 {\r
        width: 16.66666%;\r
        display: block\r
    }\r
}\r
\r
@media all and (min-width: 1100px) {\r
    .full-1100 {\r
        width: 100%;\r
        display: block\r
    }\r
\r
    .half-1100 {\r
        width: 50%;\r
        display: block\r
    }\r
\r
    .third-1100 {\r
        width: 33.33333%;\r
        display: block\r
    }\r
\r
    .two-third-1100 {\r
        width: 66.66666%;\r
        display: block\r
    }\r
\r
    .fourth-1100 {\r
        width: 25%;\r
        display: block\r
    }\r
\r
    .three-fourth-1100 {\r
        width: 75%;\r
        display: block\r
    }\r
\r
    .fifth-1100 {\r
        width: 20%;\r
        display: block\r
    }\r
\r
    .two-fifth-1100 {\r
        width: 40%;\r
        display: block\r
    }\r
\r
    .three-fifth-1100 {\r
        width: 60%;\r
        display: block\r
    }\r
\r
    .four-fifth-1100 {\r
        width: 80%;\r
        display: block\r
    }\r
\r
    .sixth-1100 {\r
        width: 16.66666%;\r
        display: block\r
    }\r
}\r
\r
@media all and (min-width: 1200px) {\r
    .full-1200 {\r
        width: 100%;\r
        display: block\r
    }\r
\r
    .half-1200 {\r
        width: 50%;\r
        display: block\r
    }\r
\r
    .third-1200 {\r
        width: 33.33333%;\r
        display: block\r
    }\r
\r
    .two-third-1200 {\r
        width: 66.66666%;\r
        display: block\r
    }\r
\r
    .fourth-1200 {\r
        width: 25%;\r
        display: block\r
    }\r
\r
    .three-fourth-1200 {\r
        width: 75%;\r
        display: block\r
    }\r
\r
    .fifth-1200 {\r
        width: 20%;\r
        display: block\r
    }\r
\r
    .two-fifth-1200 {\r
        width: 40%;\r
        display: block\r
    }\r
\r
    .three-fifth-1200 {\r
        width: 60%;\r
        display: block\r
    }\r
\r
    .four-fifth-1200 {\r
        width: 80%;\r
        display: block\r
    }\r
\r
    .sixth-1200 {\r
        width: 16.66666%;\r
        display: block\r
    }\r
}\r
\r
@media all and (min-width: 1300px) {\r
    .full-1300 {\r
        width: 100%;\r
        display: block\r
    }\r
\r
    .half-1300 {\r
        width: 50%;\r
        display: block\r
    }\r
\r
    .third-1300 {\r
        width: 33.33333%;\r
        display: block\r
    }\r
\r
    .two-third-1300 {\r
        width: 66.66666%;\r
        display: block\r
    }\r
\r
    .fourth-1300 {\r
        width: 25%;\r
        display: block\r
    }\r
\r
    .three-fourth-1300 {\r
        width: 75%;\r
        display: block\r
    }\r
\r
    .fifth-1300 {\r
        width: 20%;\r
        display: block\r
    }\r
\r
    .two-fifth-1300 {\r
        width: 40%;\r
        display: block\r
    }\r
\r
    .three-fifth-1300 {\r
        width: 60%;\r
        display: block\r
    }\r
\r
    .four-fifth-1300 {\r
        width: 80%;\r
        display: block\r
    }\r
\r
    .sixth-1300 {\r
        width: 16.66666%;\r
        display: block\r
    }\r
}\r
\r
@media all and (min-width: 1400px) {\r
    .full-1400 {\r
        width: 100%;\r
        display: block\r
    }\r
\r
    .half-1400 {\r
        width: 50%;\r
        display: block\r
    }\r
\r
    .third-1400 {\r
        width: 33.33333%;\r
        display: block\r
    }\r
\r
    .two-third-1400 {\r
        width: 66.66666%;\r
        display: block\r
    }\r
\r
    .fourth-1400 {\r
        width: 25%;\r
        display: block\r
    }\r
\r
    .three-fourth-1400 {\r
        width: 75%;\r
        display: block\r
    }\r
\r
    .fifth-1400 {\r
        width: 20%;\r
        display: block\r
    }\r
\r
    .two-fifth-1400 {\r
        width: 40%;\r
        display: block\r
    }\r
\r
    .three-fifth-1400 {\r
        width: 60%;\r
        display: block\r
    }\r
\r
    .four-fifth-1400 {\r
        width: 80%;\r
        display: block\r
    }\r
\r
    .sixth-1400 {\r
        width: 16.66666%;\r
        display: block\r
    }\r
}\r
\r
@media all and (min-width: 1500px) {\r
    .full-1500 {\r
        width: 100%;\r
        display: block\r
    }\r
\r
    .half-1500 {\r
        width: 50%;\r
        display: block\r
    }\r
\r
    .third-1500 {\r
        width: 33.33333%;\r
        display: block\r
    }\r
\r
    .two-third-1500 {\r
        width: 66.66666%;\r
        display: block\r
    }\r
\r
    .fourth-1500 {\r
        width: 25%;\r
        display: block\r
    }\r
\r
    .three-fourth-1500 {\r
        width: 75%;\r
        display: block\r
    }\r
\r
    .fifth-1500 {\r
        width: 20%;\r
        display: block\r
    }\r
\r
    .two-fifth-1500 {\r
        width: 40%;\r
        display: block\r
    }\r
\r
    .three-fifth-1500 {\r
        width: 60%;\r
        display: block\r
    }\r
\r
    .four-fifth-1500 {\r
        width: 80%;\r
        display: block\r
    }\r
\r
    .sixth-1500 {\r
        width: 16.66666%;\r
        display: block\r
    }\r
}\r
\r
@media all and (min-width: 1600px) {\r
    .full-1600 {\r
        width: 100%;\r
        display: block\r
    }\r
\r
    .half-1600 {\r
        width: 50%;\r
        display: block\r
    }\r
\r
    .third-1600 {\r
        width: 33.33333%;\r
        display: block\r
    }\r
\r
    .two-third-1600 {\r
        width: 66.66666%;\r
        display: block\r
    }\r
\r
    .fourth-1600 {\r
        width: 25%;\r
        display: block\r
    }\r
\r
    .three-fourth-1600 {\r
        width: 75%;\r
        display: block\r
    }\r
\r
    .fifth-1600 {\r
        width: 20%;\r
        display: block\r
    }\r
\r
    .two-fifth-1600 {\r
        width: 40%;\r
        display: block\r
    }\r
\r
    .three-fifth-1600 {\r
        width: 60%;\r
        display: block\r
    }\r
\r
    .four-fifth-1600 {\r
        width: 80%;\r
        display: block\r
    }\r
\r
    .sixth-1600 {\r
        width: 16.66666%;\r
        display: block\r
    }\r
}\r
\r
@media all and (min-width: 1700px) {\r
    .full-1700 {\r
        width: 100%;\r
        display: block\r
    }\r
\r
    .half-1700 {\r
        width: 50%;\r
        display: block\r
    }\r
\r
    .third-1700 {\r
        width: 33.33333%;\r
        display: block\r
    }\r
\r
    .two-third-1700 {\r
        width: 66.66666%;\r
        display: block\r
    }\r
\r
    .fourth-1700 {\r
        width: 25%;\r
        display: block\r
    }\r
\r
    .three-fourth-1700 {\r
        width: 75%;\r
        display: block\r
    }\r
\r
    .fifth-1700 {\r
        width: 20%;\r
        display: block\r
    }\r
\r
    .two-fifth-1700 {\r
        width: 40%;\r
        display: block\r
    }\r
\r
    .three-fifth-1700 {\r
        width: 60%;\r
        display: block\r
    }\r
\r
    .four-fifth-1700 {\r
        width: 80%;\r
        display: block\r
    }\r
\r
    .sixth-1700 {\r
        width: 16.66666%;\r
        display: block\r
    }\r
}\r
\r
@media all and (min-width: 1800px) {\r
    .full-1800 {\r
        width: 100%;\r
        display: block\r
    }\r
\r
    .half-1800 {\r
        width: 50%;\r
        display: block\r
    }\r
\r
    .third-1800 {\r
        width: 33.33333%;\r
        display: block\r
    }\r
\r
    .two-third-1800 {\r
        width: 66.66666%;\r
        display: block\r
    }\r
\r
    .fourth-1800 {\r
        width: 25%;\r
        display: block\r
    }\r
\r
    .three-fourth-1800 {\r
        width: 75%;\r
        display: block\r
    }\r
\r
    .fifth-1800 {\r
        width: 20%;\r
        display: block\r
    }\r
\r
    .two-fifth-1800 {\r
        width: 40%;\r
        display: block\r
    }\r
\r
    .three-fifth-1800 {\r
        width: 60%;\r
        display: block\r
    }\r
\r
    .four-fifth-1800 {\r
        width: 80%;\r
        display: block\r
    }\r
\r
    .sixth-1800 {\r
        width: 16.66666%;\r
        display: block\r
    }\r
}\r
\r
@media all and (min-width: 1900px) {\r
    .full-1900 {\r
        width: 100%;\r
        display: block\r
    }\r
\r
    .half-1900 {\r
        width: 50%;\r
        display: block\r
    }\r
\r
    .third-1900 {\r
        width: 33.33333%;\r
        display: block\r
    }\r
\r
    .two-third-1900 {\r
        width: 66.66666%;\r
        display: block\r
    }\r
\r
    .fourth-1900 {\r
        width: 25%;\r
        display: block\r
    }\r
\r
    .three-fourth-1900 {\r
        width: 75%;\r
        display: block\r
    }\r
\r
    .fifth-1900 {\r
        width: 20%;\r
        display: block\r
    }\r
\r
    .two-fifth-1900 {\r
        width: 40%;\r
        display: block\r
    }\r
\r
    .three-fifth-1900 {\r
        width: 60%;\r
        display: block\r
    }\r
\r
    .four-fifth-1900 {\r
        width: 80%;\r
        display: block\r
    }\r
\r
    .sixth-1900 {\r
        width: 16.66666%;\r
        display: block\r
    }\r
}\r
\r
@media all and (min-width: 2000px) {\r
    .full-2000 {\r
        width: 100%;\r
        display: block\r
    }\r
\r
    .half-2000 {\r
        width: 50%;\r
        display: block\r
    }\r
\r
    .third-2000 {\r
        width: 33.33333%;\r
        display: block\r
    }\r
\r
    .two-third-2000 {\r
        width: 66.66666%;\r
        display: block\r
    }\r
\r
    .fourth-2000 {\r
        width: 25%;\r
        display: block\r
    }\r
\r
    .three-fourth-2000 {\r
        width: 75%;\r
        display: block\r
    }\r
\r
    .fifth-2000 {\r
        width: 20%;\r
        display: block\r
    }\r
\r
    .two-fifth-2000 {\r
        width: 40%;\r
        display: block\r
    }\r
\r
    .three-fifth-2000 {\r
        width: 60%;\r
        display: block\r
    }\r
\r
    .four-fifth-2000 {\r
        width: 80%;\r
        display: block\r
    }\r
\r
    .sixth-2000 {\r
        width: 16.66666%;\r
        display: block\r
    }\r
}\r
\r
@media all and (min-width: 500px) {\r
    .none-500 {\r
        display: none\r
    }\r
}\r
\r
@media all and (min-width: 600px) {\r
    .none-600 {\r
        display: none\r
    }\r
}\r
\r
@media all and (min-width: 700px) {\r
    .none-700 {\r
        display: none\r
    }\r
}\r
\r
@media all and (min-width: 800px) {\r
    .none-800 {\r
        display: none\r
    }\r
}\r
\r
@media all and (min-width: 900px) {\r
    .none-900 {\r
        display: none\r
    }\r
}\r
\r
@media all and (min-width: 1000px) {\r
    .none-1000 {\r
        display: none\r
    }\r
}\r
\r
@media all and (min-width: 1100px) {\r
    .none-1100 {\r
        display: none\r
    }\r
}\r
\r
@media all and (min-width: 1200px) {\r
    .none-1200 {\r
        display: none\r
    }\r
}\r
\r
@media all and (min-width: 1300px) {\r
    .none-1300 {\r
        display: none\r
    }\r
}\r
\r
@media all and (min-width: 1400px) {\r
    .none-1400 {\r
        display: none\r
    }\r
}\r
\r
@media all and (min-width: 1500px) {\r
    .none-1500 {\r
        display: none\r
    }\r
}\r
\r
@media all and (min-width: 1600px) {\r
    .none-1600 {\r
        display: none\r
    }\r
}\r
\r
@media all and (min-width: 1700px) {\r
    .none-1700 {\r
        display: none\r
    }\r
}\r
\r
@media all and (min-width: 1800px) {\r
    .none-1800 {\r
        display: none\r
    }\r
}\r
\r
@media all and (min-width: 1900px) {\r
    .none-1900 {\r
        display: none\r
    }\r
}\r
\r
@media all and (min-width: 2000px) {\r
    .none-2000 {\r
        display: none\r
    }\r
}\r
\r
.off-none {\r
    margin-left: 0\r
}\r
\r
.off-half {\r
    margin-left: 50%\r
}\r
\r
.off-third {\r
    margin-left: 33.33333%\r
}\r
\r
.off-two-third {\r
    margin-left: 66.66666%\r
}\r
\r
.off-fourth {\r
    margin-left: 25%\r
}\r
\r
.off-three-fourth {\r
    margin-left: 75%\r
}\r
\r
.off-fifth {\r
    margin-left: 20%\r
}\r
\r
.off-two-fifth {\r
    margin-left: 40%\r
}\r
\r
.off-three-fifth {\r
    margin-left: 60%\r
}\r
\r
.off-four-fifth {\r
    margin-left: 80%\r
}\r
\r
.off-sixth {\r
    margin-left: 16.66666%\r
}\r
\r
@media all and (min-width: 500px) {\r
    .off-none-500 {\r
        margin-left: 0\r
    }\r
\r
    .off-half-500 {\r
        margin-left: 50%\r
    }\r
\r
    .off-third-500 {\r
        margin-left: 33.33333%\r
    }\r
\r
    .off-two-third-500 {\r
        margin-left: 66.66666%\r
    }\r
\r
    .off-fourth-500 {\r
        margin-left: 25%\r
    }\r
\r
    .off-three-fourth-500 {\r
        margin-left: 75%\r
    }\r
\r
    .off-fifth-500 {\r
        margin-left: 20%\r
    }\r
\r
    .off-two-fifth-500 {\r
        margin-left: 40%\r
    }\r
\r
    .off-three-fifth-500 {\r
        margin-left: 60%\r
    }\r
\r
    .off-four-fifth-500 {\r
        margin-left: 80%\r
    }\r
\r
    .off-sixth-500 {\r
        margin-left: 16.66666%\r
    }\r
}\r
\r
@media all and (min-width: 600px) {\r
    .off-none-600 {\r
        margin-left: 0\r
    }\r
\r
    .off-half-600 {\r
        margin-left: 50%\r
    }\r
\r
    .off-third-600 {\r
        margin-left: 33.33333%\r
    }\r
\r
    .off-two-third-600 {\r
        margin-left: 66.66666%\r
    }\r
\r
    .off-fourth-600 {\r
        margin-left: 25%\r
    }\r
\r
    .off-three-fourth-600 {\r
        margin-left: 75%\r
    }\r
\r
    .off-fifth-600 {\r
        margin-left: 20%\r
    }\r
\r
    .off-two-fifth-600 {\r
        margin-left: 40%\r
    }\r
\r
    .off-three-fifth-600 {\r
        margin-left: 60%\r
    }\r
\r
    .off-four-fifth-600 {\r
        margin-left: 80%\r
    }\r
\r
    .off-sixth-600 {\r
        margin-left: 16.66666%\r
    }\r
}\r
\r
@media all and (min-width: 700px) {\r
    .off-none-700 {\r
        margin-left: 0\r
    }\r
\r
    .off-half-700 {\r
        margin-left: 50%\r
    }\r
\r
    .off-third-700 {\r
        margin-left: 33.33333%\r
    }\r
\r
    .off-two-third-700 {\r
        margin-left: 66.66666%\r
    }\r
\r
    .off-fourth-700 {\r
        margin-left: 25%\r
    }\r
\r
    .off-three-fourth-700 {\r
        margin-left: 75%\r
    }\r
\r
    .off-fifth-700 {\r
        margin-left: 20%\r
    }\r
\r
    .off-two-fifth-700 {\r
        margin-left: 40%\r
    }\r
\r
    .off-three-fifth-700 {\r
        margin-left: 60%\r
    }\r
\r
    .off-four-fifth-700 {\r
        margin-left: 80%\r
    }\r
\r
    .off-sixth-700 {\r
        margin-left: 16.66666%\r
    }\r
}\r
\r
@media all and (min-width: 800px) {\r
    .off-none-800 {\r
        margin-left: 0\r
    }\r
\r
    .off-half-800 {\r
        margin-left: 50%\r
    }\r
\r
    .off-third-800 {\r
        margin-left: 33.33333%\r
    }\r
\r
    .off-two-third-800 {\r
        margin-left: 66.66666%\r
    }\r
\r
    .off-fourth-800 {\r
        margin-left: 25%\r
    }\r
\r
    .off-three-fourth-800 {\r
        margin-left: 75%\r
    }\r
\r
    .off-fifth-800 {\r
        margin-left: 20%\r
    }\r
\r
    .off-two-fifth-800 {\r
        margin-left: 40%\r
    }\r
\r
    .off-three-fifth-800 {\r
        margin-left: 60%\r
    }\r
\r
    .off-four-fifth-800 {\r
        margin-left: 80%\r
    }\r
\r
    .off-sixth-800 {\r
        margin-left: 16.66666%\r
    }\r
}\r
\r
@media all and (min-width: 900px) {\r
    .off-none-900 {\r
        margin-left: 0\r
    }\r
\r
    .off-half-900 {\r
        margin-left: 50%\r
    }\r
\r
    .off-third-900 {\r
        margin-left: 33.33333%\r
    }\r
\r
    .off-two-third-900 {\r
        margin-left: 66.66666%\r
    }\r
\r
    .off-fourth-900 {\r
        margin-left: 25%\r
    }\r
\r
    .off-three-fourth-900 {\r
        margin-left: 75%\r
    }\r
\r
    .off-fifth-900 {\r
        margin-left: 20%\r
    }\r
\r
    .off-two-fifth-900 {\r
        margin-left: 40%\r
    }\r
\r
    .off-three-fifth-900 {\r
        margin-left: 60%\r
    }\r
\r
    .off-four-fifth-900 {\r
        margin-left: 80%\r
    }\r
\r
    .off-sixth-900 {\r
        margin-left: 16.66666%\r
    }\r
}\r
\r
@media all and (min-width: 1000px) {\r
    .off-none-1000 {\r
        margin-left: 0\r
    }\r
\r
    .off-half-1000 {\r
        margin-left: 50%\r
    }\r
\r
    .off-third-1000 {\r
        margin-left: 33.33333%\r
    }\r
\r
    .off-two-third-1000 {\r
        margin-left: 66.66666%\r
    }\r
\r
    .off-fourth-1000 {\r
        margin-left: 25%\r
    }\r
\r
    .off-three-fourth-1000 {\r
        margin-left: 75%\r
    }\r
\r
    .off-fifth-1000 {\r
        margin-left: 20%\r
    }\r
\r
    .off-two-fifth-1000 {\r
        margin-left: 40%\r
    }\r
\r
    .off-three-fifth-1000 {\r
        margin-left: 60%\r
    }\r
\r
    .off-four-fifth-1000 {\r
        margin-left: 80%\r
    }\r
\r
    .off-sixth-1000 {\r
        margin-left: 16.66666%\r
    }\r
}\r
\r
@media all and (min-width: 1100px) {\r
    .off-none-1100 {\r
        margin-left: 0\r
    }\r
\r
    .off-half-1100 {\r
        margin-left: 50%\r
    }\r
\r
    .off-third-1100 {\r
        margin-left: 33.33333%\r
    }\r
\r
    .off-two-third-1100 {\r
        margin-left: 66.66666%\r
    }\r
\r
    .off-fourth-1100 {\r
        margin-left: 25%\r
    }\r
\r
    .off-three-fourth-1100 {\r
        margin-left: 75%\r
    }\r
\r
    .off-fifth-1100 {\r
        margin-left: 20%\r
    }\r
\r
    .off-two-fifth-1100 {\r
        margin-left: 40%\r
    }\r
\r
    .off-three-fifth-1100 {\r
        margin-left: 60%\r
    }\r
\r
    .off-four-fifth-1100 {\r
        margin-left: 80%\r
    }\r
\r
    .off-sixth-1100 {\r
        margin-left: 16.66666%\r
    }\r
}\r
\r
@media all and (min-width: 1200px) {\r
    .off-none-1200 {\r
        margin-left: 0\r
    }\r
\r
    .off-half-1200 {\r
        margin-left: 50%\r
    }\r
\r
    .off-third-1200 {\r
        margin-left: 33.33333%\r
    }\r
\r
    .off-two-third-1200 {\r
        margin-left: 66.66666%\r
    }\r
\r
    .off-fourth-1200 {\r
        margin-left: 25%\r
    }\r
\r
    .off-three-fourth-1200 {\r
        margin-left: 75%\r
    }\r
\r
    .off-fifth-1200 {\r
        margin-left: 20%\r
    }\r
\r
    .off-two-fifth-1200 {\r
        margin-left: 40%\r
    }\r
\r
    .off-three-fifth-1200 {\r
        margin-left: 60%\r
    }\r
\r
    .off-four-fifth-1200 {\r
        margin-left: 80%\r
    }\r
\r
    .off-sixth-1200 {\r
        margin-left: 16.66666%\r
    }\r
}\r
\r
@media all and (min-width: 1300px) {\r
    .off-none-1300 {\r
        margin-left: 0\r
    }\r
\r
    .off-half-1300 {\r
        margin-left: 50%\r
    }\r
\r
    .off-third-1300 {\r
        margin-left: 33.33333%\r
    }\r
\r
    .off-two-third-1300 {\r
        margin-left: 66.66666%\r
    }\r
\r
    .off-fourth-1300 {\r
        margin-left: 25%\r
    }\r
\r
    .off-three-fourth-1300 {\r
        margin-left: 75%\r
    }\r
\r
    .off-fifth-1300 {\r
        margin-left: 20%\r
    }\r
\r
    .off-two-fifth-1300 {\r
        margin-left: 40%\r
    }\r
\r
    .off-three-fifth-1300 {\r
        margin-left: 60%\r
    }\r
\r
    .off-four-fifth-1300 {\r
        margin-left: 80%\r
    }\r
\r
    .off-sixth-1300 {\r
        margin-left: 16.66666%\r
    }\r
}\r
\r
@media all and (min-width: 1400px) {\r
    .off-none-1400 {\r
        margin-left: 0\r
    }\r
\r
    .off-half-1400 {\r
        margin-left: 50%\r
    }\r
\r
    .off-third-1400 {\r
        margin-left: 33.33333%\r
    }\r
\r
    .off-two-third-1400 {\r
        margin-left: 66.66666%\r
    }\r
\r
    .off-fourth-1400 {\r
        margin-left: 25%\r
    }\r
\r
    .off-three-fourth-1400 {\r
        margin-left: 75%\r
    }\r
\r
    .off-fifth-1400 {\r
        margin-left: 20%\r
    }\r
\r
    .off-two-fifth-1400 {\r
        margin-left: 40%\r
    }\r
\r
    .off-three-fifth-1400 {\r
        margin-left: 60%\r
    }\r
\r
    .off-four-fifth-1400 {\r
        margin-left: 80%\r
    }\r
\r
    .off-sixth-1400 {\r
        margin-left: 16.66666%\r
    }\r
}\r
\r
@media all and (min-width: 1500px) {\r
    .off-none-1500 {\r
        margin-left: 0\r
    }\r
\r
    .off-half-1500 {\r
        margin-left: 50%\r
    }\r
\r
    .off-third-1500 {\r
        margin-left: 33.33333%\r
    }\r
\r
    .off-two-third-1500 {\r
        margin-left: 66.66666%\r
    }\r
\r
    .off-fourth-1500 {\r
        margin-left: 25%\r
    }\r
\r
    .off-three-fourth-1500 {\r
        margin-left: 75%\r
    }\r
\r
    .off-fifth-1500 {\r
        margin-left: 20%\r
    }\r
\r
    .off-two-fifth-1500 {\r
        margin-left: 40%\r
    }\r
\r
    .off-three-fifth-1500 {\r
        margin-left: 60%\r
    }\r
\r
    .off-four-fifth-1500 {\r
        margin-left: 80%\r
    }\r
\r
    .off-sixth-1500 {\r
        margin-left: 16.66666%\r
    }\r
}\r
\r
@media all and (min-width: 1600px) {\r
    .off-none-1600 {\r
        margin-left: 0\r
    }\r
\r
    .off-half-1600 {\r
        margin-left: 50%\r
    }\r
\r
    .off-third-1600 {\r
        margin-left: 33.33333%\r
    }\r
\r
    .off-two-third-1600 {\r
        margin-left: 66.66666%\r
    }\r
\r
    .off-fourth-1600 {\r
        margin-left: 25%\r
    }\r
\r
    .off-three-fourth-1600 {\r
        margin-left: 75%\r
    }\r
\r
    .off-fifth-1600 {\r
        margin-left: 20%\r
    }\r
\r
    .off-two-fifth-1600 {\r
        margin-left: 40%\r
    }\r
\r
    .off-three-fifth-1600 {\r
        margin-left: 60%\r
    }\r
\r
    .off-four-fifth-1600 {\r
        margin-left: 80%\r
    }\r
\r
    .off-sixth-1600 {\r
        margin-left: 16.66666%\r
    }\r
}\r
\r
@media all and (min-width: 1700px) {\r
    .off-none-1700 {\r
        margin-left: 0\r
    }\r
\r
    .off-half-1700 {\r
        margin-left: 50%\r
    }\r
\r
    .off-third-1700 {\r
        margin-left: 33.33333%\r
    }\r
\r
    .off-two-third-1700 {\r
        margin-left: 66.66666%\r
    }\r
\r
    .off-fourth-1700 {\r
        margin-left: 25%\r
    }\r
\r
    .off-three-fourth-1700 {\r
        margin-left: 75%\r
    }\r
\r
    .off-fifth-1700 {\r
        margin-left: 20%\r
    }\r
\r
    .off-two-fifth-1700 {\r
        margin-left: 40%\r
    }\r
\r
    .off-three-fifth-1700 {\r
        margin-left: 60%\r
    }\r
\r
    .off-four-fifth-1700 {\r
        margin-left: 80%\r
    }\r
\r
    .off-sixth-1700 {\r
        margin-left: 16.66666%\r
    }\r
}\r
\r
@media all and (min-width: 1800px) {\r
    .off-none-1800 {\r
        margin-left: 0\r
    }\r
\r
    .off-half-1800 {\r
        margin-left: 50%\r
    }\r
\r
    .off-third-1800 {\r
        margin-left: 33.33333%\r
    }\r
\r
    .off-two-third-1800 {\r
        margin-left: 66.66666%\r
    }\r
\r
    .off-fourth-1800 {\r
        margin-left: 25%\r
    }\r
\r
    .off-three-fourth-1800 {\r
        margin-left: 75%\r
    }\r
\r
    .off-fifth-1800 {\r
        margin-left: 20%\r
    }\r
\r
    .off-two-fifth-1800 {\r
        margin-left: 40%\r
    }\r
\r
    .off-three-fifth-1800 {\r
        margin-left: 60%\r
    }\r
\r
    .off-four-fifth-1800 {\r
        margin-left: 80%\r
    }\r
\r
    .off-sixth-1800 {\r
        margin-left: 16.66666%\r
    }\r
}\r
\r
@media all and (min-width: 1900px) {\r
    .off-none-1900 {\r
        margin-left: 0\r
    }\r
\r
    .off-half-1900 {\r
        margin-left: 50%\r
    }\r
\r
    .off-third-1900 {\r
        margin-left: 33.33333%\r
    }\r
\r
    .off-two-third-1900 {\r
        margin-left: 66.66666%\r
    }\r
\r
    .off-fourth-1900 {\r
        margin-left: 25%\r
    }\r
\r
    .off-three-fourth-1900 {\r
        margin-left: 75%\r
    }\r
\r
    .off-fifth-1900 {\r
        margin-left: 20%\r
    }\r
\r
    .off-two-fifth-1900 {\r
        margin-left: 40%\r
    }\r
\r
    .off-three-fifth-1900 {\r
        margin-left: 60%\r
    }\r
\r
    .off-four-fifth-1900 {\r
        margin-left: 80%\r
    }\r
\r
    .off-sixth-1900 {\r
        margin-left: 16.66666%\r
    }\r
}\r
\r
@media all and (min-width: 2000px) {\r
    .off-none-2000 {\r
        margin-left: 0\r
    }\r
\r
    .off-half-2000 {\r
        margin-left: 50%\r
    }\r
\r
    .off-third-2000 {\r
        margin-left: 33.33333%\r
    }\r
\r
    .off-two-third-2000 {\r
        margin-left: 66.66666%\r
    }\r
\r
    .off-fourth-2000 {\r
        margin-left: 25%\r
    }\r
\r
    .off-three-fourth-2000 {\r
        margin-left: 75%\r
    }\r
\r
    .off-fifth-2000 {\r
        margin-left: 20%\r
    }\r
\r
    .off-two-fifth-2000 {\r
        margin-left: 40%\r
    }\r
\r
    .off-three-fifth-2000 {\r
        margin-left: 60%\r
    }\r
\r
    .off-four-fifth-2000 {\r
        margin-left: 80%\r
    }\r
\r
    .off-sixth-2000 {\r
        margin-left: 16.66666%\r
    }\r
}\r
\r
nav {\r
    position: fixed;\r
    top: 0;\r
    left: 0;\r
    right: 0;\r
    height: 3em;\r
    padding: 0 .6em;\r
    background: #fff;\r
    box-shadow: 0 0 .2em rgba(170, 170, 170, .2);\r
    z-index: 10000;\r
    transition: all .3s;\r
    transform-style: preserve-3d\r
}\r
\r
nav .brand,\r
nav .menu,\r
nav .burger {\r
    float: right;\r
    position: relative;\r
    top: 50%;\r
    -webkit-transform: translateY(-50%);\r
    transform: translateY(-50%)\r
}\r
\r
nav .brand {\r
    font-weight: 700;\r
    float: left;\r
    padding: 0 .6em;\r
    max-width: 50%;\r
    white-space: nowrap;\r
    color: inherit\r
}\r
\r
nav .brand * {\r
    vertical-align: middle\r
}\r
\r
nav .logo {\r
    height: 2em;\r
    margin-right: .3em\r
}\r
\r
nav .select::after {\r
    height: calc(100% - 1px);\r
    padding: 0;\r
    line-height: 2.4em\r
}\r
\r
nav .menu>* {\r
    margin-right: .6em\r
}\r
\r
nav .burger {\r
    display: none\r
}\r
\r
@media all and (max-width: 60em) {\r
    nav .burger {\r
        display: inline-block;\r
        cursor: pointer;\r
        bottom: -1000em;\r
        margin: 0;\r
        -webkit-tap-highlight-color: transparent\r
    }\r
\r
    nav .burger~.menu,\r
    nav .show:checked~.burger {\r
        position: fixed;\r
        min-height: 100%;\r
        top: 0;\r
        right: 0;\r
        bottom: -1000em;\r
        margin: 0;\r
        background: #fff;\r
        transition: all .5s ease;\r
        transform: none\r
    }\r
\r
    nav .burger~.menu {\r
        z-index: 11\r
    }\r
\r
    nav .show:checked~.burger {\r
        color: transparent;\r
        width: 100%;\r
        border-radius: 0;\r
        background: rgba(17, 17, 17, .2);\r
        transition: all .5s ease\r
    }\r
\r
    nav .show~.menu {\r
        width: 70%;\r
        max-width: 300px;\r
        transform-origin: center right;\r
        transition: all .25s ease;\r
        transform: scaleX(0)\r
    }\r
\r
    nav .show~.menu>* {\r
        transform: translateX(100%);\r
        transition: all 0s ease .5s\r
    }\r
\r
    nav .show:checked~.menu>*:nth-child(1) {\r
        transition: all .5s cubic-bezier(0.645, 0.045, 0.355, 1) 0s\r
    }\r
\r
    nav .show:checked~.menu>*:nth-child(2) {\r
        transition: all .5s cubic-bezier(0.645, 0.045, 0.355, 1) .1s\r
    }\r
\r
    nav .show:checked~.menu>*:nth-child(3) {\r
        transition: all .5s cubic-bezier(0.645, 0.045, 0.355, 1) .2s\r
    }\r
\r
    nav .show:checked~.menu>*:nth-child(4) {\r
        transition: all .5s cubic-bezier(0.645, 0.045, 0.355, 1) .3s\r
    }\r
\r
    nav .show:checked~.menu>*:nth-child(5) {\r
        transition: all .5s cubic-bezier(0.645, 0.045, 0.355, 1) .4s\r
    }\r
\r
    nav .show:checked~.menu>*:nth-child(6) {\r
        transition: all .5s cubic-bezier(0.645, 0.045, 0.355, 1) .5s\r
    }\r
\r
    nav .show:checked~.menu {\r
        transform: scaleX(1)\r
    }\r
\r
    nav .show:checked~.menu>* {\r
        transform: translateX(0);\r
        transition: all .5s ease-in-out .6s\r
    }\r
\r
    nav .burger~.menu>* {\r
        display: block;\r
        margin: .3em;\r
        text-align: left;\r
        max-width: calc(100% - 0.6em)\r
    }\r
\r
    nav .burger~.menu>a {\r
        padding: .3em .9em\r
    }\r
}\r
\r
.stack,\r
.stack .toggle {\r
    margin-top: 0;\r
    margin-bottom: 0;\r
    display: block;\r
    width: 100%;\r
    text-align: left;\r
    border-radius: 0\r
}\r
\r
.stack:first-child,\r
.stack:first-child .toggle {\r
    border-top-left-radius: .2em;\r
    border-top-right-radius: .2em\r
}\r
\r
.stack:last-child,\r
.stack:last-child .toggle {\r
    border-bottom-left-radius: .2em;\r
    border-bottom-right-radius: .2em\r
}\r
\r
input.stack,\r
textarea.stack,\r
select.stack {\r
    transition: border-bottom 0 ease 0;\r
    border-bottom-width: 0\r
}\r
\r
input.stack:last-child,\r
textarea.stack:last-child,\r
select.stack:last-child {\r
    border-bottom-width: 1px\r
}\r
\r
input.stack:focus+input,\r
input.stack:focus+textarea,\r
input.stack:focus+select,\r
textarea.stack:focus+input,\r
textarea.stack:focus+textarea,\r
textarea.stack:focus+select,\r
select.stack:focus+input,\r
select.stack:focus+textarea,\r
select.stack:focus+select {\r
    border-top-color: #0074d9\r
}\r
\r
.modal .overlay~*,\r
.card {\r
    position: relative;\r
    box-shadow: none;\r
    border-radius: .2em;\r
    border: 1px solid #aaa;\r
    overflow: hidden;\r
    text-align: left;\r
    background: #fff;\r
    margin-bottom: .6em;\r
    padding: 0;\r
    transition: all .3s ease\r
}\r
\r
.modal .overlay~.hidden,\r
.hidden.card,\r
.modal .overlay~:checked+*,\r
.modal .overlay:checked+*,\r
:checked+.card {\r
    font-size: 0;\r
    padding: 0;\r
    margin: 0;\r
    border: 0\r
}\r
\r
.modal .overlay~*>*,\r
.card>* {\r
    max-width: 100%;\r
    display: block\r
}\r
\r
.modal .overlay~*>*:last-child,\r
.card>*:last-child {\r
    margin-bottom: 0\r
}\r
\r
.modal .overlay~* header,\r
.card header,\r
.modal .overlay~* section,\r
.card section,\r
.modal .overlay~*>p,\r
.card>p {\r
    padding: .6em .8em\r
}\r
\r
.modal .overlay~* section,\r
.card section {\r
    padding: .6em .8em 0\r
}\r
\r
.modal .overlay~* hr,\r
.card hr {\r
    border: none;\r
    height: 1px;\r
    background-color: #aaa\r
}\r
\r
.modal .overlay~* header,\r
.card header {\r
    font-weight: bold;\r
    position: relative;\r
    border-bottom: 1px solid #aaa\r
}\r
\r
.modal .overlay~* header h1,\r
.card header h1,\r
.modal .overlay~* header h2,\r
.card header h2,\r
.modal .overlay~* header h3,\r
.card header h3,\r
.modal .overlay~* header h4,\r
.card header h4,\r
.modal .overlay~* header h5,\r
.card header h5,\r
.modal .overlay~* header h6,\r
.card header h6 {\r
    padding: 0;\r
    margin: 0 2em 0 0;\r
    line-height: 1;\r
    display: inline-block;\r
    vertical-align: text-bottom\r
}\r
\r
.modal .overlay~* header:last-child,\r
.card header:last-child {\r
    border-bottom: 0\r
}\r
\r
.modal .overlay~* footer,\r
.card footer {\r
    padding: .8em\r
}\r
\r
.modal .overlay~* p,\r
.card p {\r
    margin: .3em 0\r
}\r
\r
.modal .overlay~* p:first-child,\r
.card p:first-child {\r
    margin-top: 0\r
}\r
\r
.modal .overlay~* p:last-child,\r
.card p:last-child {\r
    margin-bottom: 0\r
}\r
\r
.modal .overlay~*>p,\r
.card>p {\r
    margin: 0;\r
    padding-right: 2.5em\r
}\r
\r
.modal .overlay~* .close,\r
.card .close {\r
    position: absolute;\r
    top: .4em;\r
    right: .3em;\r
    font-size: 1.2em;\r
    padding: 0 .5em;\r
    cursor: pointer;\r
    width: auto\r
}\r
\r
.modal .overlay~* .close:hover,\r
.card .close:hover {\r
    color: #ff4136\r
}\r
\r
.modal .overlay~* h1+.close,\r
.card h1+.close {\r
    margin: .2em\r
}\r
\r
.modal .overlay~* h2+.close,\r
.card h2+.close {\r
    margin: .1em\r
}\r
\r
.modal .overlay~* .dangerous,\r
.card .dangerous {\r
    background: #ff4136;\r
    float: right\r
}\r
\r
.modal {\r
    text-align: center\r
}\r
\r
.modal>input {\r
    display: none\r
}\r
\r
.modal>input~* {\r
    opacity: 0;\r
    max-height: 0;\r
    overflow: hidden\r
}\r
\r
.modal .overlay {\r
    top: 0;\r
    left: 0;\r
    bottom: 0;\r
    right: 0;\r
    position: fixed;\r
    margin: 0;\r
    border-radius: 0;\r
    background: rgba(17, 17, 17, .2);\r
    transition: all .3s;\r
    z-index: 100000\r
}\r
\r
.modal .overlay:before,\r
.modal .overlay:after {\r
    display: none\r
}\r
\r
.modal .overlay~* {\r
    border: 0;\r
    position: fixed;\r
    top: 50%;\r
    left: 50%;\r
    transform: translateX(-50%) translateY(-50%) scale(0.2, 0.2);\r
    z-index: 1000000;\r
    transition: all .3s\r
}\r
\r
.modal>input:checked~* {\r
    display: block;\r
    opacity: 1;\r
    max-height: 10000px;\r
    transition: all .3s\r
}\r
\r
.modal>input:checked~.overlay~* {\r
    max-height: 90%;\r
    overflow: auto;\r
    -webkit-transform: translateX(-50%) translateY(-50%) scale(1, 1);\r
    transform: translateX(-50%) translateY(-50%) scale(1, 1)\r
}\r
\r
@media(max-width: 60em) {\r
    .modal .overlay~* {\r
        min-width: 90%\r
    }\r
}\r
\r
.dropimage {\r
    position: relative;\r
    display: block;\r
    padding: 0;\r
    padding-bottom: 56.25%;\r
    overflow: hidden;\r
    cursor: pointer;\r
    border: 0;\r
    margin: .3em 0;\r
    border-radius: .2em;\r
    background-color: #ddd;\r
    background-size: cover;\r
    background-position: center center;\r
    background-image: url(data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSI2NDAiIGhlaWdodD0iNjQwIiB2ZXJzaW9uPSIxLjEiPjxnIHN0eWxlPSJmaWxsOiMzMzMiPjxwYXRoIGQ9Ik0gMTg3IDIzMCBDIDE3NSAyMzAgMTY1IDI0MCAxNjUgMjUyIEwgMTY1IDMwMCBMIDE2NSA0MDggQyAxNjUgNDIwIDE3NSA0MzAgMTg3IDQzMCBMIDQ2MyA0MzAgQyA0NzUgNDMwIDQ4NSA0MjAgNDg1IDQwOCBMIDQ4NSAzMDAgTCA0ODUgMjUyIEMgNDg1IDI0MCA0NzUgMjMwIDQ2MyAyMzAgTCAxODcgMjMwIHogTSAzNjAgMjU2IEEgNzAgNzIgMCAwIDEgNDMwIDMyOCBBIDcwIDcyIDAgMCAxIDM2MCA0MDAgQSA3MCA3MiAwIDAgMSAyOTAgMzI4IEEgNzAgNzIgMCAwIDEgMzYwIDI1NiB6Ii8+PGNpcmNsZSBjeD0iMzYwIiBjeT0iMzMwIiByPSI0MSIvPjxwYXRoIGQ9Im0yMDUgMjI1IDUtMTAgMjAgMCA1IDEwLTMwIDAiLz48cGF0aCBkPSJNMjg1IDIwMEwyNzAgMjI1IDM3NiAyMjUgMzYxIDIwMCAyODUgMjAwek0zMTAgMjA1TDMzNyAyMDUgMzM3IDIxOCAzMTAgMjE4IDMxMCAyMDV6Ii8+PHBhdGggZD0ibTQwNSAyMjUgNS0xMCAyMCAwIDUgMTAtMzAgMCIvPjwvZz48L3N2Zz4=)\r
}\r
\r
.dropimage input {\r
    left: 0;\r
    width: 100%;\r
    height: 100%;\r
    border: 0;\r
    margin: 0;\r
    padding: 0;\r
    opacity: 0;\r
    cursor: pointer;\r
    position: absolute\r
}\r
\r
.tabs {\r
    position: relative;\r
    overflow: hidden\r
}\r
\r
.tabs>label img {\r
    float: left;\r
    margin-left: .6em\r
}\r
\r
.tabs>.row {\r
    width: calc(100% + 1.2em);\r
    display: table;\r
    table-layout: fixed;\r
    position: relative;\r
    padding-left: 0;\r
    transition: all .3s;\r
    border-spacing: 0;\r
    margin: 0\r
}\r
\r
.tabs>.row:before,\r
.tabs>.row:after {\r
    display: none\r
}\r
\r
.tabs>.row>*,\r
.tabs>.row img {\r
    display: table-cell;\r
    vertical-align: top;\r
    margin: 0;\r
    width: 100%\r
}\r
\r
.tabs>input {\r
    display: none\r
}\r
\r
.tabs>input+* {\r
    width: 100%\r
}\r
\r
.tabs>input+label {\r
    width: auto\r
}\r
\r
.two.tabs>.row {\r
    width: 200%;\r
    left: -100%\r
}\r
\r
.two.tabs>input:nth-of-type(1):checked~.row {\r
    margin-left: 100%\r
}\r
\r
.two.tabs>label img {\r
    width: 48%;\r
    margin: 4% 0 4% 4%\r
}\r
\r
.three.tabs>.row {\r
    width: 300%;\r
    left: -200%\r
}\r
\r
.three.tabs>input:nth-of-type(1):checked~.row {\r
    margin-left: 200%\r
}\r
\r
.three.tabs>input:nth-of-type(2):checked~.row {\r
    margin-left: 100%\r
}\r
\r
.three.tabs>label img {\r
    width: 30%;\r
    margin: 5% 0 5% 5%\r
}\r
\r
.four.tabs>.row {\r
    width: 400%;\r
    left: -300%\r
}\r
\r
.four.tabs>input:nth-of-type(1):checked~.row {\r
    margin-left: 300%\r
}\r
\r
.four.tabs>input:nth-of-type(2):checked~.row {\r
    margin-left: 200%\r
}\r
\r
.four.tabs>input:nth-of-type(3):checked~.row {\r
    margin-left: 100%\r
}\r
\r
.four.tabs>label img {\r
    width: 22%;\r
    margin: 4% 0 4% 4%\r
}\r
\r
.five.tabs>.row {\r
    width: 500%;\r
    left: -400%\r
}\r
\r
.five.tabs>input:nth-of-type(1):checked~.row {\r
    margin-left: 400%\r
}\r
\r
.five.tabs>input:nth-of-type(2):checked~.row {\r
    margin-left: 300%\r
}\r
\r
.five.tabs>input:nth-of-type(3):checked~.row {\r
    margin-left: 200%\r
}\r
\r
.five.tabs>input:nth-of-type(4):checked~.row {\r
    margin-left: 100%\r
}\r
\r
.five.tabs>label img {\r
    width: 18%;\r
    margin: 2% 0 2% 2%\r
}\r
\r
.six.tabs>.row {\r
    width: 600%;\r
    left: -500%\r
}\r
\r
.six.tabs>input:nth-of-type(1):checked~.row {\r
    margin-left: 500%\r
}\r
\r
.six.tabs>input:nth-of-type(2):checked~.row {\r
    margin-left: 400%\r
}\r
\r
.six.tabs>input:nth-of-type(3):checked~.row {\r
    margin-left: 300%\r
}\r
\r
.six.tabs>input:nth-of-type(4):checked~.row {\r
    margin-left: 200%\r
}\r
\r
.six.tabs>input:nth-of-type(5):checked~.row {\r
    margin-left: 100%\r
}\r
\r
.six.tabs>label img {\r
    width: 12%;\r
    margin: 1% 0 1% 1%\r
}\r
\r
.tabs>label:first-of-type img {\r
    margin-left: 0\r
}\r
\r
[data-tooltip] {\r
    position: relative\r
}\r
\r
[data-tooltip]:after,\r
[data-tooltip]:before {\r
    position: absolute;\r
    z-index: 10;\r
    opacity: 0;\r
    border-width: 0;\r
    height: 0;\r
    padding: 0;\r
    overflow: hidden;\r
    transition: opacity .6s ease, height 0s ease .6s;\r
    top: calc(100% - 6px);\r
    left: 0;\r
    margin-top: 12px\r
}\r
\r
[data-tooltip]:after {\r
    margin-left: 0;\r
    font-size: .8em;\r
    background: #111;\r
    content: attr(data-tooltip);\r
    white-space: nowrap\r
}\r
\r
[data-tooltip]:before {\r
    content: "";\r
    width: 0;\r
    height: 0;\r
    border-width: 0;\r
    border-style: solid;\r
    border-color: transparent transparent #111;\r
    margin-top: 0;\r
    left: 10px\r
}\r
\r
[data-tooltip]:hover:after,\r
[data-tooltip]:focus:after,\r
[data-tooltip]:hover:before,\r
[data-tooltip]:focus:before {\r
    opacity: 1;\r
    border-width: 6px;\r
    height: auto\r
}\r
\r
[data-tooltip]:hover:after,\r
[data-tooltip]:focus:after {\r
    padding: .45em .9em\r
}\r
\r
.tooltip-top:after,\r
.tooltip-top:before {\r
    top: auto;\r
    bottom: calc(100% - 6px);\r
    left: 0;\r
    margin-bottom: 12px\r
}\r
\r
.tooltip-top:before {\r
    border-color: #111 transparent transparent;\r
    margin-bottom: 0;\r
    left: 10px\r
}\r
\r
.tooltip-right:after,\r
.tooltip-right:before {\r
    left: 100%;\r
    margin-left: 6px;\r
    margin-top: 0;\r
    top: 0\r
}\r
\r
.tooltip-right:before {\r
    border-color: transparent #111 transparent transparent;\r
    margin-left: -6px;\r
    left: 100%;\r
    top: 7px\r
}\r
\r
.tooltip-left:after,\r
.tooltip-left:before {\r
    right: 100%;\r
    margin-right: 6px;\r
    left: auto;\r
    margin-top: 0;\r
    top: 0\r
}\r
\r
.tooltip-left:before {\r
    border-color: transparent transparent transparent #111;\r
    margin-right: -6px;\r
    right: 100%;\r
    top: 7px\r
}\r
\r
/* =========================================\r
   Margin Utility Classes\r
   .m-{n}  → margin: {n}px\r
   .mt-{n} → margin-top: {n}px\r
   .mr-{n} → margin-right: {n}px\r
   .mb-{n} → margin-bottom: {n}px\r
   .ml-{n} → margin-left: {n}px\r
   .mx-{n} → margin-left & margin-right: {n}px\r
   .my-{n} → margin-top & margin-bottom: {n}px\r
   Values: 0–20, 24, 28, 32, 36, 40, 48, 56, 64, 72, 80, 96\r
   auto variant: .m-auto, .mx-auto, .my-auto, etc.\r
========================================= */\r
\r
/* --- auto --- */\r
\r
.m-auto  { margin: auto; }\r
\r
.mt-auto { margin-top: auto; }\r
\r
.mr-auto { margin-right: auto; }\r
\r
.mb-auto { margin-bottom: auto; }\r
\r
.ml-auto { margin-left: auto; }\r
\r
.mx-auto { margin-left: auto; margin-right: auto; }\r
\r
.my-auto { margin-top: auto; margin-bottom: auto; }\r
\r
/* --- 0 --- */\r
\r
.m-0  { margin: 0; }\r
\r
.mt-0 { margin-top: 0; }\r
\r
.mr-0 { margin-right: 0; }\r
\r
.mb-0 { margin-bottom: 0; }\r
\r
.ml-0 { margin-left: 0; }\r
\r
.mx-0 { margin-left: 0; margin-right: 0; }\r
\r
.my-0 { margin-top: 0; margin-bottom: 0; }\r
\r
/* --- 1px --- */\r
\r
.m-1  { margin: 1px; }\r
\r
.mt-1 { margin-top: 1px; }\r
\r
.mr-1 { margin-right: 1px; }\r
\r
.mb-1 { margin-bottom: 1px; }\r
\r
.ml-1 { margin-left: 1px; }\r
\r
.mx-1 { margin-left: 1px; margin-right: 1px; }\r
\r
.my-1 { margin-top: 1px; margin-bottom: 1px; }\r
\r
/* --- 2px --- */\r
\r
.m-2  { margin: 2px; }\r
\r
.mt-2 { margin-top: 2px; }\r
\r
.mr-2 { margin-right: 2px; }\r
\r
.mb-2 { margin-bottom: 2px; }\r
\r
.ml-2 { margin-left: 2px; }\r
\r
.mx-2 { margin-left: 2px; margin-right: 2px; }\r
\r
.my-2 { margin-top: 2px; margin-bottom: 2px; }\r
\r
/* --- 3px --- */\r
\r
.m-3  { margin: 3px; }\r
\r
.mt-3 { margin-top: 3px; }\r
\r
.mr-3 { margin-right: 3px; }\r
\r
.mb-3 { margin-bottom: 3px; }\r
\r
.ml-3 { margin-left: 3px; }\r
\r
.mx-3 { margin-left: 3px; margin-right: 3px; }\r
\r
.my-3 { margin-top: 3px; margin-bottom: 3px; }\r
\r
/* --- 4px --- */\r
\r
.m-4  { margin: 4px; }\r
\r
.mt-4 { margin-top: 4px; }\r
\r
.mr-4 { margin-right: 4px; }\r
\r
.mb-4 { margin-bottom: 4px; }\r
\r
.ml-4 { margin-left: 4px; }\r
\r
.mx-4 { margin-left: 4px; margin-right: 4px; }\r
\r
.my-4 { margin-top: 4px; margin-bottom: 4px; }\r
\r
/* --- 5px --- */\r
\r
.m-5  { margin: 5px; }\r
\r
.mt-5 { margin-top: 5px; }\r
\r
.mr-5 { margin-right: 5px; }\r
\r
.mb-5 { margin-bottom: 5px; }\r
\r
.ml-5 { margin-left: 5px; }\r
\r
.mx-5 { margin-left: 5px; margin-right: 5px; }\r
\r
.my-5 { margin-top: 5px; margin-bottom: 5px; }\r
\r
/* --- 6px --- */\r
\r
.m-6  { margin: 6px; }\r
\r
.mt-6 { margin-top: 6px; }\r
\r
.mr-6 { margin-right: 6px; }\r
\r
.mb-6 { margin-bottom: 6px; }\r
\r
.ml-6 { margin-left: 6px; }\r
\r
.mx-6 { margin-left: 6px; margin-right: 6px; }\r
\r
.my-6 { margin-top: 6px; margin-bottom: 6px; }\r
\r
/* --- 7px --- */\r
\r
.m-7  { margin: 7px; }\r
\r
.mt-7 { margin-top: 7px; }\r
\r
.mr-7 { margin-right: 7px; }\r
\r
.mb-7 { margin-bottom: 7px; }\r
\r
.ml-7 { margin-left: 7px; }\r
\r
.mx-7 { margin-left: 7px; margin-right: 7px; }\r
\r
.my-7 { margin-top: 7px; margin-bottom: 7px; }\r
\r
/* --- 8px --- */\r
\r
.m-8  { margin: 8px; }\r
\r
.mt-8 { margin-top: 8px; }\r
\r
.mr-8 { margin-right: 8px; }\r
\r
.mb-8 { margin-bottom: 8px; }\r
\r
.ml-8 { margin-left: 8px; }\r
\r
.mx-8 { margin-left: 8px; margin-right: 8px; }\r
\r
.my-8 { margin-top: 8px; margin-bottom: 8px; }\r
\r
/* --- 9px --- */\r
\r
.m-9  { margin: 9px; }\r
\r
.mt-9 { margin-top: 9px; }\r
\r
.mr-9 { margin-right: 9px; }\r
\r
.mb-9 { margin-bottom: 9px; }\r
\r
.ml-9 { margin-left: 9px; }\r
\r
.mx-9 { margin-left: 9px; margin-right: 9px; }\r
\r
.my-9 { margin-top: 9px; margin-bottom: 9px; }\r
\r
/* --- 10px --- */\r
\r
.m-10  { margin: 10px; }\r
\r
.mt-10 { margin-top: 10px; }\r
\r
.mr-10 { margin-right: 10px; }\r
\r
.mb-10 { margin-bottom: 10px; }\r
\r
.ml-10 { margin-left: 10px; }\r
\r
.mx-10 { margin-left: 10px; margin-right: 10px; }\r
\r
.my-10 { margin-top: 10px; margin-bottom: 10px; }\r
\r
/* --- 11px --- */\r
\r
.m-11  { margin: 11px; }\r
\r
.mt-11 { margin-top: 11px; }\r
\r
.mr-11 { margin-right: 11px; }\r
\r
.mb-11 { margin-bottom: 11px; }\r
\r
.ml-11 { margin-left: 11px; }\r
\r
.mx-11 { margin-left: 11px; margin-right: 11px; }\r
\r
.my-11 { margin-top: 11px; margin-bottom: 11px; }\r
\r
/* --- 12px --- */\r
\r
.m-12  { margin: 12px; }\r
\r
.mt-12 { margin-top: 12px; }\r
\r
.mr-12 { margin-right: 12px; }\r
\r
.mb-12 { margin-bottom: 12px; }\r
\r
.ml-12 { margin-left: 12px; }\r
\r
.mx-12 { margin-left: 12px; margin-right: 12px; }\r
\r
.my-12 { margin-top: 12px; margin-bottom: 12px; }\r
\r
/* --- 13px --- */\r
\r
.m-13  { margin: 13px; }\r
\r
.mt-13 { margin-top: 13px; }\r
\r
.mr-13 { margin-right: 13px; }\r
\r
.mb-13 { margin-bottom: 13px; }\r
\r
.ml-13 { margin-left: 13px; }\r
\r
.mx-13 { margin-left: 13px; margin-right: 13px; }\r
\r
.my-13 { margin-top: 13px; margin-bottom: 13px; }\r
\r
/* --- 14px --- */\r
\r
.m-14  { margin: 14px; }\r
\r
.mt-14 { margin-top: 14px; }\r
\r
.mr-14 { margin-right: 14px; }\r
\r
.mb-14 { margin-bottom: 14px; }\r
\r
.ml-14 { margin-left: 14px; }\r
\r
.mx-14 { margin-left: 14px; margin-right: 14px; }\r
\r
.my-14 { margin-top: 14px; margin-bottom: 14px; }\r
\r
/* --- 15px --- */\r
\r
.m-15  { margin: 15px; }\r
\r
.mt-15 { margin-top: 15px; }\r
\r
.mr-15 { margin-right: 15px; }\r
\r
.mb-15 { margin-bottom: 15px; }\r
\r
.ml-15 { margin-left: 15px; }\r
\r
.mx-15 { margin-left: 15px; margin-right: 15px; }\r
\r
.my-15 { margin-top: 15px; margin-bottom: 15px; }\r
\r
/* --- 16px --- */\r
\r
.m-16  { margin: 16px; }\r
\r
.mt-16 { margin-top: 16px; }\r
\r
.mr-16 { margin-right: 16px; }\r
\r
.mb-16 { margin-bottom: 16px; }\r
\r
.ml-16 { margin-left: 16px; }\r
\r
.mx-16 { margin-left: 16px; margin-right: 16px; }\r
\r
.my-16 { margin-top: 16px; margin-bottom: 16px; }\r
\r
/* --- 17px --- */\r
\r
.m-17  { margin: 17px; }\r
\r
.mt-17 { margin-top: 17px; }\r
\r
.mr-17 { margin-right: 17px; }\r
\r
.mb-17 { margin-bottom: 17px; }\r
\r
.ml-17 { margin-left: 17px; }\r
\r
.mx-17 { margin-left: 17px; margin-right: 17px; }\r
\r
.my-17 { margin-top: 17px; margin-bottom: 17px; }\r
\r
/* --- 18px --- */\r
\r
.m-18  { margin: 18px; }\r
\r
.mt-18 { margin-top: 18px; }\r
\r
.mr-18 { margin-right: 18px; }\r
\r
.mb-18 { margin-bottom: 18px; }\r
\r
.ml-18 { margin-left: 18px; }\r
\r
.mx-18 { margin-left: 18px; margin-right: 18px; }\r
\r
.my-18 { margin-top: 18px; margin-bottom: 18px; }\r
\r
/* --- 19px --- */\r
\r
.m-19  { margin: 19px; }\r
\r
.mt-19 { margin-top: 19px; }\r
\r
.mr-19 { margin-right: 19px; }\r
\r
.mb-19 { margin-bottom: 19px; }\r
\r
.ml-19 { margin-left: 19px; }\r
\r
.mx-19 { margin-left: 19px; margin-right: 19px; }\r
\r
.my-19 { margin-top: 19px; margin-bottom: 19px; }\r
\r
/* --- 20px --- */\r
\r
.m-20  { margin: 20px; }\r
\r
.mt-20 { margin-top: 20px; }\r
\r
.mr-20 { margin-right: 20px; }\r
\r
.mb-20 { margin-bottom: 20px; }\r
\r
.ml-20 { margin-left: 20px; }\r
\r
.mx-20 { margin-left: 20px; margin-right: 20px; }\r
\r
.my-20 { margin-top: 20px; margin-bottom: 20px; }\r
\r
/* --- 24px --- */\r
\r
.m-24  { margin: 24px; }\r
\r
.mt-24 { margin-top: 24px; }\r
\r
.mr-24 { margin-right: 24px; }\r
\r
.mb-24 { margin-bottom: 24px; }\r
\r
.ml-24 { margin-left: 24px; }\r
\r
.mx-24 { margin-left: 24px; margin-right: 24px; }\r
\r
.my-24 { margin-top: 24px; margin-bottom: 24px; }\r
\r
/* --- 28px --- */\r
\r
.m-28  { margin: 28px; }\r
\r
.mt-28 { margin-top: 28px; }\r
\r
.mr-28 { margin-right: 28px; }\r
\r
.mb-28 { margin-bottom: 28px; }\r
\r
.ml-28 { margin-left: 28px; }\r
\r
.mx-28 { margin-left: 28px; margin-right: 28px; }\r
\r
.my-28 { margin-top: 28px; margin-bottom: 28px; }\r
\r
/* --- 32px --- */\r
\r
.m-32  { margin: 32px; }\r
\r
.mt-32 { margin-top: 32px; }\r
\r
.mr-32 { margin-right: 32px; }\r
\r
.mb-32 { margin-bottom: 32px; }\r
\r
.ml-32 { margin-left: 32px; }\r
\r
.mx-32 { margin-left: 32px; margin-right: 32px; }\r
\r
.my-32 { margin-top: 32px; margin-bottom: 32px; }\r
\r
/* --- 36px --- */\r
\r
.m-36  { margin: 36px; }\r
\r
.mt-36 { margin-top: 36px; }\r
\r
.mr-36 { margin-right: 36px; }\r
\r
.mb-36 { margin-bottom: 36px; }\r
\r
.ml-36 { margin-left: 36px; }\r
\r
.mx-36 { margin-left: 36px; margin-right: 36px; }\r
\r
.my-36 { margin-top: 36px; margin-bottom: 36px; }\r
\r
/* --- 40px --- */\r
\r
.m-40  { margin: 40px; }\r
\r
.mt-40 { margin-top: 40px; }\r
\r
.mr-40 { margin-right: 40px; }\r
\r
.mb-40 { margin-bottom: 40px; }\r
\r
.ml-40 { margin-left: 40px; }\r
\r
.mx-40 { margin-left: 40px; margin-right: 40px; }\r
\r
.my-40 { margin-top: 40px; margin-bottom: 40px; }\r
\r
/* --- 48px --- */\r
\r
.m-48  { margin: 48px; }\r
\r
.mt-48 { margin-top: 48px; }\r
\r
.mr-48 { margin-right: 48px; }\r
\r
.mb-48 { margin-bottom: 48px; }\r
\r
.ml-48 { margin-left: 48px; }\r
\r
.mx-48 { margin-left: 48px; margin-right: 48px; }\r
\r
.my-48 { margin-top: 48px; margin-bottom: 48px; }\r
\r
/* --- 56px --- */\r
\r
.m-56  { margin: 56px; }\r
\r
.mt-56 { margin-top: 56px; }\r
\r
.mr-56 { margin-right: 56px; }\r
\r
.mb-56 { margin-bottom: 56px; }\r
\r
.ml-56 { margin-left: 56px; }\r
\r
.mx-56 { margin-left: 56px; margin-right: 56px; }\r
\r
.my-56 { margin-top: 56px; margin-bottom: 56px; }\r
\r
/* --- 64px --- */\r
\r
.m-64  { margin: 64px; }\r
\r
.mt-64 { margin-top: 64px; }\r
\r
.mr-64 { margin-right: 64px; }\r
\r
.mb-64 { margin-bottom: 64px; }\r
\r
.ml-64 { margin-left: 64px; }\r
\r
.mx-64 { margin-left: 64px; margin-right: 64px; }\r
\r
.my-64 { margin-top: 64px; margin-bottom: 64px; }\r
\r
/* --- 72px --- */\r
\r
.m-72  { margin: 72px; }\r
\r
.mt-72 { margin-top: 72px; }\r
\r
.mr-72 { margin-right: 72px; }\r
\r
.mb-72 { margin-bottom: 72px; }\r
\r
.ml-72 { margin-left: 72px; }\r
\r
.mx-72 { margin-left: 72px; margin-right: 72px; }\r
\r
.my-72 { margin-top: 72px; margin-bottom: 72px; }\r
\r
/* --- 80px --- */\r
\r
.m-80  { margin: 80px; }\r
\r
.mt-80 { margin-top: 80px; }\r
\r
.mr-80 { margin-right: 80px; }\r
\r
.mb-80 { margin-bottom: 80px; }\r
\r
.ml-80 { margin-left: 80px; }\r
\r
.mx-80 { margin-left: 80px; margin-right: 80px; }\r
\r
.my-80 { margin-top: 80px; margin-bottom: 80px; }\r
\r
/* --- 96px --- */\r
\r
.m-96  { margin: 96px; }\r
\r
.mt-96 { margin-top: 96px; }\r
\r
.mr-96 { margin-right: 96px; }\r
\r
.mb-96 { margin-bottom: 96px; }\r
\r
.ml-96 { margin-left: 96px; }\r
\r
.mx-96 { margin-left: 96px; margin-right: 96px; }\r
\r
.my-96 { margin-top: 96px; margin-bottom: 96px; }\r
\r
.my-container {
  font-family: Arial, sans-serif;
}\r
\r
.my-error-text {
  color: red;
}\r
\r
.progress-bar-container {
  width: 300px;
  height: 20px;
  background-color: #e0e0e0;
  border-radius: 10px;
  overflow: hidden;
  display: inline-block;
  vertical-align: middle;
}\r
\r
.progress-bar-fill {
  height: 100%;
  background-color: #4caf50;
  border-radius: 10px;
  transition: width 0.3s ease;
}\r
\r
.progress-label {
  margin-left: 10px;
  font-weight: bold;
  vertical-align: middle;
}\r
\r
.loader {
  --dim: 1.5rem;
  width: var(--dim);
  height: var(--dim);
  position: relative;
  animation: spin988 2s linear infinite;
}\r
\r
.loader .circle {
  --color: lch(76.01% 51.3 81.39);
  --dim: 0.6rem;
  width: var(--dim);
  height: var(--dim);
  background-color: var(--color);
  border-radius: 50%;
  position: absolute;
}\r
\r
.loader .circle:nth-child(1) {
  top: 0;
  left: 0;
}\r
\r
.loader .circle:nth-child(2) {
  top: 0;
  right: 0;
}\r
\r
.loader .circle:nth-child(3) {
  bottom: 0;
  left: 0;
}\r
\r
.loader .circle:nth-child(4) {
  bottom: 0;
  right: 0;
}\r
\r
@keyframes spin988 {
  0% {
    transform: scale(1) rotate(0);
  }
  20%, 25% {
    transform: scale(1.3) rotate(90deg);
  }
  45%, 50% {
    transform: scale(1) rotate(180deg);
  }
  70%, 75% {
    transform: scale(1.3) rotate(270deg);
  }
  95%, 100% {
    transform: scale(1) rotate(360deg);
  }
}`],encapsulation:3});let hs=Re;function hb(n,t){const e=n.clone({setHeaders:{"CUSTOM-HEADER":"CUTE"}});return console.log("HTTP Request:",e),t(e).pipe(_p(r=>{r instanceof Ir&&console.log("HTTP Response:",r)}),dc(r=>(console.error("HTTP Error:",r),mp(()=>r))))}Cw(hs,{providers:[eb(rb([hb]))]}).catch(n=>console.error(n));
