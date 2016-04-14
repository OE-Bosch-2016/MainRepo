package hu.nik.project.ebs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import hu.nik.project.environment.objects.SceneObject;

public class EmergencyBreakSystemMessagePackage implements Serializable {
	public Boolean breaking;		//to break or not to break. that is the question

	public EmergencyBreakSystemMessagePackage(boolean EBSState)
	{
		this.breaking=EBSState;
	}

}
