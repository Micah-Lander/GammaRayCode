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
        driveForward(0.5, 1.75); // Example: drive forward at 0.5 speed to 0.5 feet
      //  driveForward(-0.5, 1.0);
        
         //sleep(6000);
        // telemetry.addData("Path", "Complete");
        // telemetry.update();
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
        double targetPositionInches = target * 12;
        double ticksPerRev = 560/2.3; // According to REV docs if we have a 20:1 gear ratio
        double targetPositionWheelRotations = targetPositionInches / (1.5 * Math.PI);
        int targetPositionTicks = (int) (targetPositionWheelRotations * ticksPerRev);

        // Resets the encoder positions to make sure the target position is accurate
        frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        telemetry.addData("Status", "Encoders reset");
        telemetry.update();

        // Assigns the target position to each motor
        frontLeftDrive.setTargetPosition(targetPositionTicks);
        frontRightDrive.setTargetPosition(targetPositionTicks);
        backLeftDrive.setTargetPosition(targetPositionTicks);
        backRightDrive.setTargetPosition(targetPositionTicks);
        telemetry.addData("Status", "Target position set");
        telemetry.update();

while (frontLeftDrive.getCurrentPosition() < targetPositionTicks) {
        // Tells the motors to go
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        telemetry.addData("Status", "Encoders running to position");
        telemetry.update();

        // Max speed the motors can run at
        frontLeftDrive.setPower(speed);
        frontRightDrive.setPower(speed);
        backLeftDrive.setPower(speed);
        backRightDrive.setPower(speed);
        telemetry.addData("Status", "Did it work?");
        telemetry.update();
}
    }   
}
