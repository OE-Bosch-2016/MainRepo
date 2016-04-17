package hu.nik.project.ebs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import hu.nik.project.environment.objects.SceneObject;

public class EmergencyBreakSystemMessagePackage implements Serializable {
	public float deceleration;		//to break or not to break. that is the question

	public EmergencyBreakSystemMessagePackage(float deceleration)
	{
		this.deceleration=deceleration;
	}

}
