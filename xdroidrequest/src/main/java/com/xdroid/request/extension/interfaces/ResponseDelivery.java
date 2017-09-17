/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xdroid.request.extension.interfaces;

import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.config.DataType;

/**
 *  Used to cache thread or request thread after the completion of the delivery data
 * @author Robin
 * @since 2015-05-08 18:04:42
 * @param <ReturnDataType>
 */
public interface ResponseDelivery <ReturnDataType>{
    /**
     * Parses a response from the network or cache and delivers it.
     */
    public void postResponse(XDroidRequest<?> request, ReturnDataType response, DataType dataType);


    /**
     * Posts an error for the given request.
     */
    public void postError(XDroidRequest<?> request, String error);
}
