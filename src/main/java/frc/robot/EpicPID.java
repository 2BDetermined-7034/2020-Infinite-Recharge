/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * Add your docs here.
 */
public class EpicPID {
    private double kP;
    private double kD;
    private double kI;

    private double target;
    private double max;
    private double threshold;
    private double rateThreshold = 1;

    private double lastTime = 0;
    private double lastError = 0;
    private double lastRate = 0;

    public EpicPID(double kP, double kD, double kI, double target, double maxOutput, double threshold) {
        this.kP = kP;
        this.kD = kD;
        this.kI = kI;
        this.target = target;
        this.max = maxOutput;
    }

    public double calculate(double input, double time) {
        double error = input - target;
        double dt = time - lastTime;
        double rate = (dt != 0) ? (error - lastError)/dt : 0;
        double accel = (dt != 0) ? (rate - lastRate)/dt : 0;
        
        lastError = error;
        lastTime = time;
        lastRate = rate;
        return -Shortcuts.bound(kP*error/100 + kD*rate + kI*error*dt, max);
    }

    public void setTarget(double newTarget) { this.target = newTarget; }

    public boolean isDone() {
        System.out.println(Math.abs(lastError) <= threshold && Math.abs(lastRate) <= rateThreshold);
        return Math.abs(lastError) <= threshold && Math.abs(lastRate) <= rateThreshold;
    }
}
