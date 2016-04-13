package hu.nik.project.ebs;

import java.util.ArrayList;
import java.util.List;
import hu.nik.project.environment.objects.SceneObject;

public class EmergencyBreakSystemMessagePackage {
	public Boolean breaking;		//to break or not to break. that is the question
	
	public void EmergencyBreakSystemMessagePackage(SceneObject car, List<SceneObject> jaywalk){
		this.car = car;
		this.jaywalkers = jaywalk;
	}
}
