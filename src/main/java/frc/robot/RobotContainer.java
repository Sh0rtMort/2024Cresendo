// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import frc.robot.commands.Autos;
import frc.robot.commands.ElevatorCommand;
import frc.robot.commands.ElevatorManual;
import frc.robot.commands.FireShooter;
import frc.robot.commands.RevShooter;
import frc.robot.commands.TeleopSwerve;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Swerve;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.PS4Controller.Button;
import edu.wpi.first.wpilibj.simulation.XboxControllerSim;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;


public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Swerve swerve = new Swerve();

  private final ElevatorSubsystem elevatorSubsystem = new ElevatorSubsystem();

  private final Shooter shooter = new Shooter();

  private final XboxController driver = new XboxController(0);
  private final XboxController operator = new XboxController(1);

    /* Drive Controls */
  private final int translationAxis = XboxController.Axis.kLeftY.value;
  private final int strafeAxis = XboxController.Axis.kLeftX.value;
  private final int rotationAxis = XboxController.Axis.kRightX.value;

    /* Driver Buttons */
  private final JoystickButton zeroGyro = new JoystickButton(driver, 7); //options button(two squares)
  private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kStart.value);
  private final JoystickButton fireShooter = new JoystickButton(driver, XboxController.Button.kRightBumper.value);

  private final JoystickButton elevatorHigh = new JoystickButton(operator, XboxController.Button.kY.value);
  private final JoystickButton elevatorStore = new JoystickButton(operator, XboxController.Button.kA.value);
  private final JoystickButton zeroEncoders = new JoystickButton(operator, 7);
  
 
  private SendableChooser<Command> autoChooser = new SendableChooser<>();

  
  public RobotContainer() {
    // Configure the trigger bindings
    swerve.setDefaultCommand(
      new TeleopSwerve(
          swerve, 
          () -> -driver.getRawAxis(translationAxis), 
          () -> -driver.getRawAxis(strafeAxis), 
          () -> -driver.getRawAxis(rotationAxis), 
          () -> robotCentric.getAsBoolean()
      )
  );

    configureBindings();

    autoChooser.addOption("Nothing", null);

    autoChooser.addOption("Move Out", Autos.SimpleMoveAuto(swerve));

    SmartDashboard.putData(autoChooser);

    elevatorSubsystem.resetEncoders();

    elevatorSubsystem.setDefaultCommand(new ElevatorManual(elevatorSubsystem, operator, 0.35));

  }

 
  private void configureBindings() {
    zeroGyro.onTrue(new InstantCommand(() -> swerve.zeroGyro()));
    zeroEncoders.onTrue(new InstantCommand(() -> elevatorSubsystem.resetEncoders()));

    fireShooter.onTrue(new FireShooter(shooter));

    elevatorHigh.onTrue(new ElevatorCommand(elevatorSubsystem, 200));
    elevatorStore.onTrue(new ElevatorCommand(elevatorSubsystem, 5));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }
}
