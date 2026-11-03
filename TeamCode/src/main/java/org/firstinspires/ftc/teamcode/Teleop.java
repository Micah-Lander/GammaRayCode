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

@TeleOp(name="Gamma Ray Teleop")
public class Teleop extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private Robot robot;
    private Gamepad driverController;
    private Gamepad operatorController;
    private double shooterPower;
    private double intakePower;

    @Override
    public void runOpMode() {
        // Initialize controllers
        driverController = new Gamepad();
        operatorController = new Gamepad();
        driverController.setGamepadID(0);
        operatorController.setGamepadID(1);
        robot = new Robot();

        robot.init();
        //Telemetry prints status updates and sensor data to the driver hub
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Robot code won't start until the start button on the driver hub is pressed
        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            telemetry.addData("Status", "Made it to the active loop");
            robot.driveRobot(driverController.left_stick_y, driverController.left_stick_x, driverController.right_stick_x);
            
            //Shooter is set to use the right trigger right now
            if(operatorController.right_trigger > 0.05){
                shooterPower = 0.6;
                robot.setShooterPower(shooterPower);
            } else{
                shooterPower = 0.0;
                robot.setShooterPower(shooterPower);
            }

            //Intake is set to use button b right now
            if (operatorController.b){
                intakePower = 0.6;
                robot.setIntakePower(intakePower);
            } else{
                intakePower = 0.0;
                robot.setIntakePower(intakePower);
            }
            telemetry.update();
        }
    }
}
