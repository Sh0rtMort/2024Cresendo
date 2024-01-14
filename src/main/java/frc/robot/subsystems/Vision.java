package frc.robot.subsystems;

import java.util.List;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.VisionConstants;

public class Vision extends SubsystemBase{

    private PhotonCamera limelight = new PhotonCamera(NetworkTableInstance.getDefault(), "limelight");
    
    public Vision() {

        var result = limelight.getLatestResult();
        boolean hasTarget = limelight.hasTargets();
        List<PhotonTrackedTarget> targets = result.getTargets();
        PhotonTrackedTarget bestTarget = result.getBestTarget();
        double yaw = bestTarget.getYaw();
        double pitch = bestTarget.getPitch();
        double area = bestTarget.getArea();
        double skew = bestTarget.getSkew();
        // Transform2d pose = bestTarget.getCameraToTarget();
        // List<TargetCorner> corners = bestTarget.getCorners();

        int targetID = bestTarget.getFiducialId();
        double poseAmbiguity = bestTarget.getPoseAmbiguity();
        Transform3d bestCameraToTarget = bestTarget.getBestCameraToTarget();
        Transform3d alternateCameraToTarget = bestTarget.getAlternateCameraToTarget();

        // Pose3d robotPose = PhotonUtils.estimateFieldToRobotAprilTag(
        //     bestTarget.getBestCameraToTarget(), 
        //     aprilTagFieldLayout.getTagPose(bestTarget.getFiducialId()),
        //     cameraToRobot
        // );

        PhotonUtils.calculateDistanceToTargetMeters(poseAmbiguity, targetID, pitch, targetID);
    }

    public CommandBase visionTrack() {
        return run(() -> {
            var result = limelight.getLatestResult();
            if (result.hasTargets()) {
                double range = PhotonUtils.calculateDistanceToTargetMeters(
                    VisionConstants.CameraHeightMeters,
                     VisionConstants.TargetHeightMeters,
                      VisionConstants.CameraPitchRadians, 
                      Units.degreesToRadians(result.getBestTarget().getPitch())
                );
            }
        });
    }

}
