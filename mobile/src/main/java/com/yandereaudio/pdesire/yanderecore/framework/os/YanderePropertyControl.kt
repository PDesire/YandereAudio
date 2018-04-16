package com.yandereaudio.pdesire.yanderecore.framework.os

import com.yandereaudio.pdesire.yanderecore.framework.os.YandereRootUtility

object YanderePropertyControl {
    fun getprop(property : String) : String {
        try {
            val propertyValue = System.getProperty(property)
            if (propertyValue != null)
                return propertyValue
            else
                return ""
        } catch (e0 : NullPointerException){}

        return ""
    }

    fun setprop (property : String, value : String) {
        YandereRootUtility.sudo("setprop " + property + " " + value)
    }
}