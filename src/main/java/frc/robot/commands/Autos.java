// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Swerve;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public final class Autos {
  /** Example static factory for an autonomous command. */
  public static CommandBase SimpleMoveAuto(Swerve s_Swerve) {
    return Commands.sequence(new InstantCommand(() -> s_Swerve.drive(new Translation2d(0.15 / 20,0), 0, false, true)));
  }

  private Autos() {
    throw new UnsupportedOperationException("This is a utility class!");
  }
}
