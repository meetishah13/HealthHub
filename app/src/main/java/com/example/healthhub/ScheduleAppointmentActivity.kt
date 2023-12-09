package com.example.healthhub

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Camera
import android.graphics.Color
import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.graphics.Typeface
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CaptureRequest
import android.media.ImageReader
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.HandlerThread
import android.util.TypedValue
import android.view.Surface
import android.view.TextureView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.healthhub.fragments.TimePickerFragment
import org.w3c.dom.Text
import java.io.File
import java.io.FileOutputStream
import java.util.Calendar
import java.util.*

class ScheduleAppointmentActivity : AppCompatActivity() {
    lateinit var capReq:CaptureRequest.Builder
    lateinit var handler: Handler
    lateinit var handThread: HandlerThread

    lateinit var cameraManager: CameraManager
    lateinit var textureView: TextureView
    lateinit var cameraCaptureSession: CameraCaptureSession
    lateinit var cameraDevice: CameraDevice
    lateinit var captureRequest: CaptureRequest
    lateinit var imageReadable: ImageReader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_appointment)
        //Calendar
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        get_permissions()


        textureView = findViewById(R.id.textureView)
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        handThread = HandlerThread("videoThread")
        handThread.start()
        handler =Handler((handThread).looper)
        textureView.surfaceTextureListener = object: TextureView.SurfaceTextureListener{
            override fun onSurfaceTextureAvailable(
                surface: SurfaceTexture,
                width: Int,
                height: Int
            ) {
                open_camera()
            }

            override fun onSurfaceTextureSizeChanged(
                surface: SurfaceTexture,
                width: Int,
                height: Int
            ) {

            }

            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
               return false
            }

            override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {

            }
        }
        imageReadable=ImageReader.newInstance(1080,1920,ImageFormat.JPEG,1)
        imageReadable.setOnImageAvailableListener(object : ImageReader.OnImageAvailableListener {
            override fun onImageAvailable(p0: ImageReader?) {
                var image = p0?.acquireLatestImage()
                var buffer = image!!.planes[0].buffer
                var bytes = ByteArray(buffer.remaining())
                buffer.get(bytes)
                var files = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),"img.jpeg")
                var opStream = FileOutputStream(files)
                opStream.write(bytes)
                opStream.close()
                image.close()
                Toast.makeText(this@ScheduleAppointmentActivity,"image capture",Toast.LENGTH_SHORT).show()

            }
        },handler)

        findViewById<Button>(R.id.Capture).apply {
            setOnClickListener {
                capReq = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
                capReq.addTarget(imageReadable.surface)
                cameraCaptureSession.capture(capReq.build(),null,null)

            }
        }

                //button click for how date Picker dialog
        findViewById<Button>(R.id.pickDateBtn).setOnClickListener {

            val dpd=DatePickerDialog(this,DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                findViewById<TextView>(R.id.dateInput).setText("" + mDay +"/" +mMonth +"/" + mYear )
            },year,month,day)
            dpd.show()
        }
//        findViewById<Button>(R.id.doctortime).setOnClickListener{
//
////            val fm = supportFragmentManager
////            val timePickerDialogFragment = TimePickerFragment()
////            timePickerDialogFragment.show(fm, "time_picker")
//
//        }
        val timePickerButton: Button = findViewById(R.id.doctortime)
        val outputTxt: TextView = findViewById(R.id.timeInput)

        timePickerButton.setOnClickListener {
            // Create a TimePickerDialog
            val timePickerDialog = TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    // Handle the time selection
                    val selectedTime = "Your Selected Time: $hourOfDay:$minute"
                    outputTxt.setTextColor(Color.GREEN)
                    outputTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
                    outputTxt.typeface = Typeface.MONOSPACE
                    outputTxt.text = selectedTime
                },
                // Set the current time as the default values
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                true // 24-hour format
            )

            // Show the TimePickerDialog
            timePickerDialog.show()
        }

    }
    fun setSelectedTime(hourOfDay: Int, minute: Int) {
        val selectedTime: String = "Your Selected Time $hourOfDay : $minute"
        var output_txt: TextView = findViewById<TextView>(R.id.timeInput)
        output_txt.setTextColor(Color.BLUE)
        output_txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
        output_txt.typeface = Typeface.MONOSPACE
        output_txt.text = selectedTime
    }
    fun get_permissions(){

        var permissionsLst= mutableListOf<String>()
        if(checkSelfPermission(android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED)
            permissionsLst.add(android.Manifest.permission.CAMERA)
        if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
            permissionsLst.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        if(checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
            permissionsLst.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if(permissionsLst.size>0){
            requestPermissions(permissionsLst.toTypedArray(),101)
        }
    }

    @SuppressLint("MissingPermission")
    fun open_camera(){
        cameraManager.openCamera(cameraManager.cameraIdList[0],object:CameraDevice.StateCallback(){

            override fun onOpened(p0: CameraDevice) {
                cameraDevice = p0
                capReq = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
                var surface = Surface(textureView.surfaceTexture)
                capReq.addTarget(surface)
                cameraDevice.createCaptureSession(listOf(surface,imageReadable.surface), object:
                    CameraCaptureSession.StateCallback() {
                    override fun onConfigured(p0: CameraCaptureSession) {
                        cameraCaptureSession = p0
                        cameraCaptureSession.setRepeatingRequest(capReq.build(), null, null)
                    }

                    override fun onConfigureFailed(session: CameraCaptureSession) {
                    }
                }, handler )


            }
            override fun onDisconnected(camera: CameraDevice) {

            }

            override fun onError(camera: CameraDevice, error: Int) {

            }
        },handler )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        grantResults.forEach {
            if(it!=PackageManager.PERMISSION_GRANTED){
                get_permissions()
            }
        }
    }
}
