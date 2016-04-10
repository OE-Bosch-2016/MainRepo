package EBS;

import java.util.List;
import hu.nik.project.environment.objects.SceneObject;

public interface IEmergencyBreakSystem {
	public boolean get_EBS_State(SceneObject car, List<SceneObject> jay_walkers);
}
