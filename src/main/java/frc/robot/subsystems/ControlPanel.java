/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

public class ControlPanel extends SubsystemBase {
  /**
   * Creates a new ControlPanel.
   */

  private final ColorSensorV3  m_colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
  private final ColorMatch m_colorMatcher = new ColorMatch();

  /*
  private final Color redTarget = ColorMatch.makeColor(.561, .232, .114);
  private final Color yellowTarget = ColorMatch.makeColor(.361, .524, .113);
  private final Color greenTarget = ColorMatch.makeColor(.197, .561, .240);
  private final Color blueTarget = ColorMatch.makeColor(.143, .427, .429);
  */

  private final Color redTarget = ColorMatch.makeColor(.530, .344, .124);
  private final Color yellowTarget = ColorMatch.makeColor(.327, .560, .113);
  private final Color greenTarget = ColorMatch.makeColor(.158, .565, .267);
  private final Color blueTarget = ColorMatch.makeColor(.123, .453, .424);

  public ControlPanel() {
    m_colorMatcher.addColorMatch(redTarget);
    m_colorMatcher.addColorMatch(yellowTarget);
    m_colorMatcher.addColorMatch(greenTarget);
    m_colorMatcher.addColorMatch(blueTarget);
  }

  @Override
  public void periodic() {
    Color detectedColor = m_colorSensor.getColor();

    ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

    String detectedColorName =
      match.confidence < .92 ? "UNKNOWN" : 
      match.color == redTarget ? "RED" :
      match.color == yellowTarget ? "YELLOW" :
      match.color == greenTarget ? "GREEN" :
      match.color == blueTarget ? "BLUE" : "UNKNOWN";

    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);

    SmartDashboard.putString("Detected Color", detectedColorName);
    SmartDashboard.putNumber("Confidence", match.confidence);

    SmartDashboard.putString("FINAL DETECTED COLOR", detectedColorName);
  }
}
