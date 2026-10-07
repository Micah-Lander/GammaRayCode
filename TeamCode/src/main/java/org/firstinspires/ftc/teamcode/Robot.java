package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

public class Robot {

    private final LinearOpMode opMode;
    // Define drive motors
    private DcMotor frontLeftDrive;
    private DcMotor frontRightDrive;
    private DcMotor backLeftDrive;
    private DcMotor backRightDrive;

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
        // Initialize drive motors
        frontLeftDrive  = opMode.HardwareMap.get(DcMotor.class, "frontLeftDrive");
        frontRightDrive = opMode.HardwareMap.get(DcMotor.class, "frontRightDrive");
        backLeftDrive = opMode.HardwareMap.get(DcMotor.class, "backLeftDrive");
        backRightDrive = opMode.HardwareMap.get(DcMotor.class, "backRightDrive");

        //If the robot drives backwards, reverse the left side motors instead
        frontRightDrive.setDirection(DcMotor.Direction.REVERSE);
        backRightDrive.setDirection(DcMotor.Direction.REVERSE);

        // We're using encoders... right??
        // I wrote these in here for when we're ready for encoders, but for our first test they may not be implemented yet.
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Telemetry! For those unfamiliar, telemetry is just a print out of data or a status update
        opMode.telemetry.addData(">", "Hardware Initialized");
        opMode.telemetry.update();
    }

     public void driveRobot(double y, double x, double rx) {
            // maxPower is the largest motor power possible (absolute value) 1 or 100%
            double maxPower = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            
            //Basically what this does is it reads the value of the joysticks (a value between -1 and 1) 
            //in each direction (x is horizontal, y is vertical, and rx is rotational) 
            //and then divides it by the max total power (100% speed represented by 1.0). 
            double frontLeftPower = (y + x + rx) / maxPower;
            double backLeftPower = (y - x + rx) / maxPower;
            double frontRightPower = (y - x - rx) / maxPower;
            double backRightPower = (y + x - rx) / maxPower;

            // Output the values to the motor drives.
            frontLeftDrive.setPower(frontLeftPower);
            backLeftDrive.setPower(backLeftPower);
            frontRightDrive.setPower(frontRightPower);
            backRightDrive.setPower(backRightPower);
    }
}
