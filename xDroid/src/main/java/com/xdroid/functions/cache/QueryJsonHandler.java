package com.xdroid.functions.cache;

import com.xdroid.functions.request.JsonData;

public abstract class QueryJsonHandler implements QueryHandler<JsonData> {

    @Override
    public JsonData processRawDataFromCache(JsonData rawData) {
        return rawData;
    }
}
