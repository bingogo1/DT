package bin.g11n.gt.util;

import org.aspectj.lang.JoinPoint;

import bin.g11n.gt.web.action.BaseAction;


public class ActionProfiler {
	public void before(JoinPoint point) {
		BaseAction action = (BaseAction) point.getTarget();
		String method = point.getSignature().getName();
		//System.out.println("Profiler: "+action.getClass().getName()+" method: "+method+" is called at "+new Date().toString());
	}

	public void after(JoinPoint point) {
		BaseAction action = (BaseAction) point.getTarget();
		String method = point.getSignature().getName();
		//System.out.println("Profiler: "+action.getClass().getName()+" method: "+method+" is finished at "+new Date().toString());
	}
}
