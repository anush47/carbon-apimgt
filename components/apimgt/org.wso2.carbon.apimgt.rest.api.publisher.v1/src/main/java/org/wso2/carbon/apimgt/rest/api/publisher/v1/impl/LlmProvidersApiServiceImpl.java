package org.wso2.carbon.apimgt.rest.api.publisher.v1.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.apimgt.api.APIAdmin;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.model.LLMProvider;
import org.wso2.carbon.apimgt.impl.APIAdminImpl;
import org.wso2.carbon.apimgt.rest.api.publisher.v1.*;
import org.wso2.carbon.apimgt.rest.api.publisher.v1.dto.*;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.wso2.carbon.apimgt.rest.api.publisher.v1.dto.LLMProviderSummaryResponseListDTO;
import org.wso2.carbon.apimgt.rest.api.util.utils.RestApiUtil;

import java.util.List;
import javax.ws.rs.core.Response;

public class LlmProvidersApiServiceImpl implements LlmProvidersApiService {

    private static final Log log = LogFactory.getLog(LlmProvidersApiServiceImpl.class);

    @Override
    public Response getLLMProvider(String llmProviderId, MessageContext messageContext) throws APIManagementException {

        APIAdmin apiAdmin = new APIAdminImpl();
        String organization = RestApiUtil.getValidatedOrganization(messageContext);
        try {
            LLMProviderResponseDTO result =
                    LLMProviderMappingUtil.fromProviderToProviderResponseDTO(apiAdmin.getLLMProvider(organization,
                            llmProviderId));
            return Response.ok().entity(result).build();
        } catch (APIManagementException e) {
            log.warn("Error while retrieving LLM Provider");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    public Response getLLMProviders(MessageContext messageContext) throws APIManagementException {

        APIAdmin apiAdmin = new APIAdminImpl();
        String organization = RestApiUtil.getValidatedOrganization(messageContext);
        try {
            List<LLMProvider> LLMProviderList = apiAdmin.getLLMProvidersByOrg(organization);
            LLMProviderSummaryResponseListDTO providerListDTO =
                    LLMProviderMappingUtil.fromProviderSummaryListToProviderSummaryListDTO(LLMProviderList);
            return Response.ok().entity(providerListDTO).build();
        } catch (APIManagementException e) {
            log.warn("Error while trying to retrieve LLM Providers");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
