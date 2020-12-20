package com.example.rpcfx.api;

public interface RpcfxResolver {

    <T> T resolve(String serviceClass) throws Exception;

}
