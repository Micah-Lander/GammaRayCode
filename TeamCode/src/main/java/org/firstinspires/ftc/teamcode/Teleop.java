package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name="Teleop", group="Teleop")
public class Teleop extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
   // Robot robot;
    private DcMotor frontLeftDrive;
    private DcMotor frontRightDrive;
    private DcMotor backLeftDrive;
    private DcMotor backRightDrive;

    private Gamepad driverController;
    private Gamepad operatorController;

    @Override
    public void runOpMode() {
        // Initialize controllers
        driverController = new Gamepad();
        operatorController = new Gamepad();

        // Initialize drive motors
        frontLeftDrive  = hardwareMap.get(DcMotor.class, "frontLeftDrive");
        frontRightDrive = hardwareMap.get(DcMotor.class, "frontRightDrive");
        backLeftDrive = hardwareMap.get(DcMotor.class, "backLeftDrive");
        backRightDrive = hardwareMap.get(DcMotor.class, "backRightDrive");

        //If the robot drives backwards, reverse the left side motors instead
        frontRightDrive.setDirection(DcMotor.Direction.REVERSE);
        backRightDrive.setDirection(DcMotor.Direction.REVERSE);


        // We're using encoders... right??
        // I wrote these in here for when we're ready for encoders, but for our first test they may not be implemented yet.
        // frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Telemetry prints status updates and sensor data to the driver hub
        telemetry.addData("Status", "Initialized");
        // Robot code won't start until the start button on the driver hub is pressed
        waitForStart();
        runtime.reset();
        telemetry.update();

        while (opModeIsActive()) {
            driveRobot(driverController.left_stick_y, driverController.left_stick_x, driverController.right_stick_x);
        }
    }

    public void driveRobot(double y, double x, double rx) {
            // maxPower is the largest motor power (absolute value) or 1
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
