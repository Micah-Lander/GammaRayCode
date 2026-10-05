package org.firstinspires.ftc.robotcontroller.external.samples.externalhardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

public class Robot {

    private final LinearOpMode opMode;
    // Define drive motors
    private DcMotor frontLeftDrive;
    private DcMotor frontRightDrive;
    private DcMotor backLeftDrive;
    private DcMotor backRightDrive;

    private Gamepad driverController;
    private Gamepad operatorController;

    // Define a constructor that allows the OpMode to pass a reference to itself.
    public Robot (LinearOpMode opMode) {
        this.opMode = opMode;
    }

    /**
     * Initialize all the robot's hardware.
     * This method must be called ONCE when the OpMode is initialized.
     * <p>
     * All of the hardware devices are accessed via the hardware map, and initialized.
     */
    public void init()    {
        // Initialize controllers
        driverController = new Gamepad();
        operatorController = newGamepad();

        // Initialize drive motors
        frontLeftDrive  = opMode.hardwareMap.get(DcMotor.class, "frontLeftDrive");
        frontRightDrive = opMode.hardwareMap.get(DcMotor.class, "frontRightDrive");
        backLeftDrive = opMode.hardwareMap.get(DcMotor.class, "backLeftDrive");
        backRightDrive = opMode.hardwareMap.get(DcMotor.class, "backRightDrive");

        //If the robot drives backwards, reverse the left side motors instead
        frontRightDrive.setDirection(DcMotor.Direction.REVERSE);
        backRightDrive.setDirection(DcMotor.Direction.REVERSE);


        // We're using encoders... right??
        // I wrote these in here for when we're ready for encoders, but for our first test they may not be implemented yet.
        // frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Telemetry! For those unfamiliar, telemetry is just a print out of data or a status update
        opMode.telemetry.addData(">", "Hardware Initialized");
        opMode.telemetry.update();
    }

    /**
     * Calculates the left/right motor powers required to achieve the requested
     * robot motions: Drive (Axial motion) and Turn (Yaw motion).
     * Then sends these power levels to the motors.
     *
     * @param Drive     Fwd/Rev driving power (-1.0 to 1.0) +ve is forward
     * @param Turn      Right/Left turning power (-1.0 to 1.0) +ve is CW
     */
    public void driveRobot(double Drive, double Turn) {
            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            // Output the values to the motor drives.
            frontLeftDrive.setPower(frontLeftPower);
            backLeftDrive.setPower(backLeftPower);
            frontRightDrive.setPower(frontRightPower);
            backRightDrive.setPower(backRightPower);
    }
}
