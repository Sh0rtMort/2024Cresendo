package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.MotorCommutation;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMaxLowLevel.PeriodicFrame;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.DeviceConstants;

public class ElevatorSubsystem extends SubsystemBase{

    private CANSparkMax elevator1 = new CANSparkMax(DeviceConstants.elevator1, MotorType.kBrushless);
    private CANSparkMax elevator2 = new CANSparkMax(DeviceConstants.elevator2, MotorType.kBrushless);

    public ElevatorSubsystem() {

        elevator1.setInverted(true);
        elevator2.setInverted(false);

        elevator1.setIdleMode(IdleMode.kBrake);
        elevator2.setIdleMode(IdleMode.kBrake);

        elevator1.setClosedLoopRampRate(0);
        elevator1.setOpenLoopRampRate(0);
        elevator2.setClosedLoopRampRate(0);
        elevator2.setOpenLoopRampRate(0);

        elevator1.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 60);
        elevator2.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 60);
    }
    
    public void setElevatorSpeed(double speed) {
        elevator1.set(speed);
        elevator2.set(speed);
    }

    public void resetEncoders() {
        elevator1.getEncoder().setPosition(0);
        elevator2.getEncoder().setPosition(0);
    }
}
