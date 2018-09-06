package com.pinterest.demo.Exceptions;

import java.io.IOException;

/**
 * Created by Pravesh Sharma on 04-09-2018.
 */
public class NoConnectivityException extends IOException {

    @Override
    public String getMessage() {
        return "No connectivity exception";
    }

}