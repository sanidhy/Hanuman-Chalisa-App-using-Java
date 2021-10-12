package com.example.aarti_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    public Button button;
    public void sendAppItself() {
        ApplicationInfo app = getApplicationContext().getApplicationInfo();
        String filePath = app.sourceDir;

        Intent intent = new Intent(Intent.ACTION_SEND);

        // MIME of .apk is "application/vnd.android.package-archive".
        // but Bluetooth does not accept this. Let's use "*/*" instead.
        intent.setType("*/*");

        // Append file and send Intent
        File originalApk = new File(filePath);

        try {
            File tempFile = new File(getExternalCacheDir() + "/ExtractedApk");
            if (!tempFile.isDirectory())
                if (!tempFile.mkdirs())
                    return;
            tempFile = new File(tempFile.getPath() + "/" + getString(app.labelRes).replace(" ","").toLowerCase() + ".apk");
            if (!tempFile.exists()) {
                if (!tempFile.createNewFile()) {
                    return;
                }
            }
            InputStream in = new FileInputStream(originalApk);
            OutputStream out = new FileOutputStream(tempFile);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            System.out.println("File copied.");
            //Open share dialog
//          intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(tempFile));
            Uri photoURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", tempFile);
//          intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(tempFile));
            intent.putExtra(Intent.EXTRA_STREAM, photoURI);
            startActivity(Intent.createChooser(intent, "Share app via"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_main);
//        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        getSupportActionBar().hide();
        button=(Button)findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });

        ImageView leftIcon=findViewById(R.id.left_icon);
        leftIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawerLayout=findViewById(R.id.drawer_layout);
                drawerLayout.openDrawer(GravityCompat.START);
//                Toast.makeText(MainActivity.this,"Back",Toast.LENGTH_SHORT).show();
            }
        });

        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.nav_home)
                {
                    Toast.makeText(MainActivity.this,"होम पेज",Toast.LENGTH_SHORT).show();
                }

                else if(item.getItemId()==R.id.nav_slideshow)
                {
                    Toast.makeText(MainActivity.this,"हनुमान चालीसा",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                    startActivity(intent);
                }
                else if(item.getItemId()==R.id.nav_gallery)
                {
                    Toast.makeText(MainActivity.this,"हनुमानजी की आरती",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.this,Main5Activity.class);
                    startActivity(intent);
                }
                else if(item.getItemId()==R.id.listen_chalisa)
                {
                    Toast.makeText(MainActivity.this,"हनुमानजी चालीसा",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.this,Main8Activity.class);
                    startActivity(intent);
                }
                else if(item.getItemId()==R.id.listen_aarti)
                {
                    Toast.makeText(MainActivity.this,"हनुमानजी की आरती",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.this,Main7Activity.class);
                    startActivity(intent);
                }
                else if(item.getItemId()==R.id.share)
                {
                    try {
                        sendAppItself();
                    }
                    catch(Exception e)
                    {
                        Toast.makeText(MainActivity.this,"Unable",Toast.LENGTH_SHORT).show();
                    }

                }
                else if(item.getItemId()==R.id.nav_share)
                {
                    Toast.makeText(MainActivity.this,"हनुमान चालीसा के बारे में तथ्य",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.this,Main9Activity.class);
                    startActivity(intent);
                }
                else if(item.getItemId()==R.id.nav_send)
                {

                    Intent intent=new Intent(MainActivity.this,Main10Activity.class);
                    startActivity(intent);
                }

                DrawerLayout drawerLayout=findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });



    }
}
