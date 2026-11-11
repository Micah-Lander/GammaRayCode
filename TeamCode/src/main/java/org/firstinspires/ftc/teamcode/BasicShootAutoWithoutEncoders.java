package org.firstinspires.ftc.teamcode.BasicShootAutoWithoutEncoders;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

// This auto is adapted from the RobotAutoDriveByEncoder_Linear.java example

@Autonomous(name="Basic Shoot Auto (Time Based)", group="Robot")

public class BasicShootAutoWithoutEncoders extends LinearOpMode {

    /* Declare OpMode members. */
    private DcMotor frontLeftDrive;
    private DcMotor frontRightDrive;
    private DcMotor backLeftDrive;
    private DcMotor backRightDrive;
    private DcMotor shooterMotor;
    private DcMotor intakeMotor;

    private double driveGearRatio = 20; // Gear ratio guess, high key don't know if it's right or not

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        // Initialize the drive system variables.
        frontLeftDrive  = hardwareMap.get(DcMotor.class, "frontLeftDrive");
        frontRightDrive = hardwareMap.get(DcMotor.class, "frontRightDrive");
        backLeftDrive   = hardwareMap.get(DcMotor.class, "backLeftDrive");
        backRightDrive  = hardwareMap.get(DcMotor.class, "backRightDrive");

        //Define mechanism motors
        shooterMotor = hardwareMap.get(DcMotor.class, "shooterMotor");
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");

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
        driveForward(0.5, 0.82); // Forward roughly 2 feet
        runtime.reset();        
        strafeRight(0.5, 1.23); // Strafe right roughly 3 feet
        runtime.reset();
        shoot(0.8, 0.5); //80% speed for half a second
        runtime.reset();
        strafeLeft(0.5, 3.29); // Strafe left roughly 8 feet
        runtime.reset();
        driveBackward(0.5, 2.46); // Drive backward roughly 6 feet

        telemetry.addData("Path", "Complete");
        telemetry.update();
    }

    public void driveForward(double time, double speed){
        
        while (runtime.seconds() < time){
        frontLeftDrive.setPower(speed);
        frontRightDrive.setPower(speed);
        backLeftDrive.setPower(speed);
        backRightDrive.setPower(speed);
        }
}
    public void driveBackward(double time, double speed){
        
        while (runtime.seconds() < time){
        frontLeftDrive.setPower(-speed);
        frontRightDrive.setPower(-speed);
        backLeftDrive.setPower(-speed);
        backRightDrive.setPower(-speed);
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
    public void shoot(double time, double speed){
        
        while (runtime.seconds() < time){
        shooterMotor.setPower(speed);
        }
}
    public void intake(double time, double speed){
        
        while (runtime.seconds() < time){
        intakeMotor.setPower(speed);
        }
}
}
