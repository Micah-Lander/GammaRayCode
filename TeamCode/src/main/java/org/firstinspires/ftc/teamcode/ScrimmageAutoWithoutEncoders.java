package org.firstinspires.ftc.teamcode.ScrimmageAutoWithoutEncoders;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

// This auto is adapted from the RobotAutoDriveByEncoder_Linear.java example

@Autonomous(name="Scrimmage Auto Without Encoders", group="Robot")

public class ScrimmageAutoWithoutEncoders extends LinearOpMode {

    /* Declare OpMode members. */
    private DcMotor frontLeftDrive;
    private DcMotor frontRightDrive;
    private DcMotor backLeftDrive;
    private DcMotor backRightDrive;

    private double driveGearRatio = 20; // Gear ratio guess, high key don't know if it's right or not

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
        
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        backRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        telemetry.update();

        waitForStart();
        runtime.reset();

        //NOTE: Right now this takes ticks, not feet. I need to do the conversion math still.
        driveForward(0.5, 0.6); // Example: drive forward for 3 seconds at 0.6 speed
        strafeRight(0.5, 0.6);

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

    public void driveForward(double time, double speed){
        
        while (runtime.seconds() < time){
        frontLeftDrive.setPower(speed);
        frontRightDrive.setPower(speed);
        backLeftDrive.setPower(speed);
        backRightDrive.setPower(speed);
        }
}

    public void strafeRight(double time, double speed){
        while (runtime.seconds() < time){
        frontLeftDrive.setPower(speed);
        frontRightDrive.setPower(-speed);
        backLeftDrive.setPower(-speed);
        backRightDrive.setPower(speed);
        }
}
    public void strafeLeft(double time, double speed){
        
        while (runtime.seconds() < time){
        frontLeftDrive.setPower(-speed);
        frontRightDrive.setPower(speed);
        backLeftDrive.setPower(speed);
        backRightDrive.setPower(speed);
        }
}
}
