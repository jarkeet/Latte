package com.cmcc.hy.latte.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.cmcc.hy.latte.app.Latte;
import com.cmcc.hy.latte.net.callback.IRequest;
import com.cmcc.hy.latte.net.callback.ISuccess;
import com.cmcc.hy.latte.util.file.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by jeff on 2018/5/11.
 */

public class SaveFileTask extends AsyncTask<Object, Void, File>{


    private final IRequest mIRequest;
    private final ISuccess mISuccess;

    public SaveFileTask(IRequest IRequest, ISuccess ISuccess) {
        mIRequest = IRequest;
        mISuccess = ISuccess;
    }

    @Override
    protected File doInBackground(Object... objects) {
        String downloadDir = (String) objects[0];
        String extension = (String) objects[1];
        final ResponseBody body = (ResponseBody) objects[2];
        final String name = (String) objects[3];
        final InputStream is = body.byteStream();

        if(downloadDir == null || downloadDir.equals("")) {
            downloadDir = "down_loads";
        }

        if(extension == null && extension.equals("")) {
            extension = "";
        }
        if(name == null) {
            return FileUtil.writeToDisk(is, downloadDir, extension.toUpperCase(), extension);
        } else {
            return FileUtil.writeToDisk(is, downloadDir, name);
        }
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if(mISuccess != null) {
            mISuccess.onSuccess(file.getAbsolutePath());
        }
        if(mIRequest != null) {
            mIRequest.onRequestEnd();
        }
        autoInstallApk(file);
    }

    private void autoInstallApk(File file) {
        if(FileUtil.getExtension(file.getPath()).equals("apk")) {
            final Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            Latte.getApplicationContext().startActivity(install);
        }
    }
}
