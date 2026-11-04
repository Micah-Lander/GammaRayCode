package org.firstinspires.ftc.teamcode.ScrimmageAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

// This auto is adapted from the RobotAutoDriveByEncoder_Linear.java example

@Autonomous(name="Scrimmage Auto", group="Robot")

public class ScrimmageAuto extends LinearOpMode {

    /* Declare OpMode members. */
    private DcMotor frontLeftDrive;
    private DcMotor frontRightDrive;
    private DcMotor backLeftDrive;
    private DcMotor backRightDrive;

    private double driveGearRatio = 5.0; // Gear ratio guess, high key don't know if it's right or not

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        // Initialize the drive system variables.
        frontLeftDrive  = hardwareMap.get(DcMotor.class, "frontLeftDrive");
        frontRightDrive = hardwareMap.get(DcMotor.class, "frontRightDrive");
        backLeftDrive   = hardwareMap.get(DcMotor.class, "backLeftDrive");
        backRightDrive  = hardwareMap.get(DcMotor.class, "backRightDrive");

        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // When run, this OpMode should start both motors driving forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        frontLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        frontRightDrive.setDirection(DcMotor.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        backRightDrive.setDirection(DcMotor.Direction.REVERSE);

        frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Starting at",  "%7d :%7d",
                          frontLeftDrive.getCurrentPosition(),
                          frontRightDrive.getCurrentPosition(),
                          backLeftDrive.getCurrentPosition(),
                          backRightDrive.getCurrentPosition());
        telemetry.update();

        waitForStart();

        //NOTE: Right now this takes ticks, not feet. I need to do the conversion math still.
        driveForward(0.5, 0.5); // Example: drive forward at 0.5 speed to 0.5 feet

        telemetry.addData("Path", "Complete");
        telemetry.update();
    }

    /*
     *  Method to perform a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the OpMode running.
     */
    public void driveForward(double speed, double target) {
        double targetPositionFeet = target;
        double ticksPerRev = 28 * driveGearRatio; // REV Ultraplanetary Motor has a base tick count of 28 per revolution, need to multiply it by gear ratio though
        //NOTE: When measuring the radius of the wheel, get it in feet, not inches
        double distancePerTickInFeet = (0.25 * 3.1415) / ticksPerRev; // Convert ticks per revolution to ticks per foot based on wheel circumference
        double targetPositionTicks = targetPositionFeet / distancePerTickInFeet;
        double distanceTravelled = frontLeftDrive.getCurrentPosition(); // Get the current position in ticks
        double distanceToTarget = targetPositionTicks - distanceTravelled;

        if (distanceToTarget > 0) {
            frontLeftDrive.setPower(speed);
            frontRightDrive.setPower(speed);
            backLeftDrive.setPower(speed);
            backRightDrive.setPower(speed);
        } else {
            frontLeftDrive.setPower(0);
            frontRightDrive.setPower(0);
            backLeftDrive.setPower(0);
            backRightDrive.setPower(0);
        }
    }
}
