package com.pdesire.projectcluster.framework

class ClusterPropertyControl {
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
}