package frc.robot.subsystems;

import java.lang.Character.Subset;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.MotorCommutation;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMaxLowLevel.PeriodicFrame;

import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.DeviceConstants;
import frc.robot.Constants.ShootingConstants;

public class Shooter extends SubsystemBase{

    private TalonFX shootWheel = new TalonFX(DeviceConstants.shootWheel);
    private TalonFX indexWheel = new TalonFX(DeviceConstants.indexWheel);

    private TalonFX storageIndexMotor = new TalonFX(5);
    
    public Shooter() {
        shootWheel.setInverted(false);
        indexWheel.setInverted(false);

        shootWheel.configOpenloopRamp(0);
        shootWheel.configClosedloopRamp(0);
        indexWheel.configOpenloopRamp(0);
        indexWheel.configClosedloopRamp(0);

        shootWheel.setNeutralMode(NeutralMode.Coast);
        indexWheel.setNeutralMode(NeutralMode.Coast);

        storageIndexMotor.setInverted(false);

        storageIndexMotor.setNeutralMode(NeutralMode.Brake);

        storageIndexMotor.configClosedloopRamp(0);
        storageIndexMotor.configOpenloopRamp(0);
    }

    public void setDualSpeedPercent(double speed) {
        shootWheel.set(ControlMode.PercentOutput, speed);
        indexWheel.set(ControlMode.PercentOutput, speed);
    }

    public void setShooterRPM(double rpm) {
        double motorSpeed = (2048 / 600.0) * rpm;

        shootWheel.set(ControlMode.Velocity, motorSpeed);

        SmartDashboard.putNumber("Shooter RPM", ( (600.0 / 2048) * shootWheel.getSelectedSensorVelocity()));
    }
    
    public void setIndexRPM(double rpm) {
        double motorSpeed = (2048 / 600.0) * rpm;

        shootWheel.set(ControlMode.Velocity, motorSpeed);

        SmartDashboard.putNumber("Index Wheel RPM", ((600.0 / 2048) * indexWheel.getSelectedSensorVelocity()));
    }

    public void stop() {
        shootWheel.set(ControlMode.Velocity, 0);
        indexWheel.set(ControlMode.Velocity, 0);
    }

    public void setStorageIndexRPM(double rpm) {
        double motorSpeed = (2048 / 600) * rpm;

        storageIndexMotor.set(ControlMode.Velocity, motorSpeed);

        SmartDashboard.putNumber("Storage Index RPM", ((600 / 2048) * storageIndexMotor.getSelectedSensorVelocity()));
    }

    public Command getTopIntakeCommand() {
        // The startEnd helper method takes a method to call when the command is initialized and one to
        // call when it ends
        return this.startEnd(
            () -> {
              setShooterRPM(ShootingConstants.IntakeShooterSpeed);
              setIndexRPM(ShootingConstants.IntakeIndexSpeed);
            },
            () -> {
              stop();
            });
      }

    public Command getBottomIntakeCommand() {
        return this.startEnd(
            () -> {
                setStorageIndexRPM(ShootingConstants.StorageIndexSpeed);
            }, () -> {
                setStorageIndexRPM(0);
            });
    }
}
