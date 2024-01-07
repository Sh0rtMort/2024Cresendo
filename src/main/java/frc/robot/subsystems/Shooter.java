package frc.robot.subsystems;

import java.lang.Character.Subset;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.DeviceConstants;

public class Shooter extends SubsystemBase{

    private TalonFX shootWheel = new TalonFX(DeviceConstants.shootWheel);
    private TalonFX indexWheel = new TalonFX(DeviceConstants.indexWheel);
    
    public Shooter() {
        shootWheel.setInverted(false);
        indexWheel.setInverted(false);

        shootWheel.configOpenloopRamp(0);
        shootWheel.configClosedloopRamp(0);
        indexWheel.configOpenloopRamp(0);
        indexWheel.configClosedloopRamp(0);

        shootWheel.setNeutralMode(NeutralMode.Coast);
        indexWheel.setNeutralMode(NeutralMode.Coast);
    }

    public void setDualSpeedPercent(double speed) {
        shootWheel.set(ControlMode.PercentOutput, speed);
        indexWheel.set(ControlMode.PercentOutput, speed);
    }

    public void setShooterRPM(double rpm) {
        double motorSpeed = (2048 / 600.0) * rpm;

        shootWheel.set(ControlMode.Velocity, motorSpeed);

        SmartDashboard.putNumber("RPM", ( (600.0 / 2048) * shootWheel.getSelectedSensorVelocity()));
    }
    
    public void setIndexRPM(double rpm) {
        double motorSpeed = (2048 / 600.0) * rpm;

        shootWheel.set(ControlMode.Velocity, motorSpeed);

        SmartDashboard.putNumber("RPM", ( (600.0 / 2048) * indexWheel.getSelectedSensorVelocity()));
    }
}
