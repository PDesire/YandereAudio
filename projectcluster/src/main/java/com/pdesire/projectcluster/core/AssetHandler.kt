/*
 * Copyright (C) 2017-2018 Tristan Marsell, All rights reserved.
 *
 * This code is licensed under the PPL (PDesire Public License) License
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.pdesire.projectcluster.core

import android.content.Context
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


/**
 * Created by pdesire on 10.12.17.
 */

class AssetHandler(val context: Context) {

    @Throws(IOException::class)
    fun copyDirorfileFromAssetManager(arg_assetDir: String, arg_destinationDir: String): String {
        val dest_dir_path = "/magisk/shinka_pd/system"
        val dest_dir = File(dest_dir_path)

        createDir(dest_dir)

        val asset_manager = context.applicationContext.getAssets()
        val files = asset_manager.list(arg_assetDir)

        for (i in files.indices) {

            val abs_asset_file_path = addTrailingSlash(arg_assetDir) + files[i]
            val sub_files = asset_manager.list(abs_asset_file_path)

            if (sub_files.size == 0) {
                // It is a file
                val dest_file_path = addTrailingSlash(dest_dir_path) + files[i]
                copyAssetFile(abs_asset_file_path, dest_file_path)
            } else {
                // It is a sub directory
                copyDirorfileFromAssetManager(abs_asset_file_path, addTrailingSlash(arg_destinationDir) + files[i])
            }
        }

        return dest_dir_path
    }


    @Throws(IOException::class)
    fun copyAssetFile(assetFilePath: String, destinationFilePath: String) {
        val inside = context.applicationContext.getAssets().open(assetFilePath)
        val out = FileOutputStream(destinationFilePath)

        val buf = ByteArray(1024)
        val len = inside.read(buf)
        while (len > 0)
            out.write(buf, 0, len)
        inside.close()
        out.close()
    }

    fun addTrailingSlash(path: String): String {
        var newpath = path
        if (newpath[newpath.length - 1] != '/') {
            newpath += "/"
        }
        return newpath
    }

    fun addLeadingSlash(path: String): String {
        var newpath = path
        if (newpath[0] != '/') {
            newpath = "/" + path
        }
        return newpath
    }

    @Throws(IOException::class)
    fun createDir(dir: File) {
        if (dir.exists()) {
            if (!dir.isDirectory()) {
                throw IOException("Can't create directory, a file is in the way")
            }
        } else {
            dir.mkdirs()
            if (!dir.isDirectory()) {
                throw IOException("Unable to create directory")
            }
        }
    }
}
