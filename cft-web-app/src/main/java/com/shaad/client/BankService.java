package com.shaad.client;

import com.shaad.client.entities.Bank;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

@Path("banks")
interface BankService extends RestService {

    @GET
    @Path("?text={text}")
    void getCities(@PathParam("text") final String text, MethodCallback<List<Bank>> callback);
}
