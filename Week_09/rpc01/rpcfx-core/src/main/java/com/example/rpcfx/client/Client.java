package com.example.rpcfx.client;

import com.example.rpcfx.api.RpcfxRequest;
import com.example.rpcfx.api.RpcfxResponse;

import java.io.IOException;

public interface Client {

    RpcfxResponse invoke(RpcfxRequest req, String url) throws IOException;
}
