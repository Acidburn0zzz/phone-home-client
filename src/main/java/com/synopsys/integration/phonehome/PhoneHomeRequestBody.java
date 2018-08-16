/**
 * phone-home-client
 *
 * Copyright (C) 2018 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.synopsys.integration.phonehome;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.apache.commons.lang3.StringUtils;

import com.synopsys.integration.phonehome.enums.ProductIdEnum;

public class PhoneHomeRequestBody {
    public static final PhoneHomeRequestBody DO_NOT_PHONE_HOME = null;

    private final String customerId;
    private final String hostName;
    private final String artifactId;
    private final String artifactVersion;
    private final ProductIdEnum productId;
    private final String productVersion;
    private final Map<String, String> metaData;

    private PhoneHomeRequestBody(final PhoneHomeRequestBody.Builder builder) {
        this.customerId = builder.getCustomerId();
        this.hostName = builder.getHostName();
        this.artifactId = builder.getArtifactId();
        this.artifactVersion = builder.getArtifactVersion();
        this.productId = builder.getProductId();
        this.productVersion = builder.getProductVersion();
        this.metaData = Collections.unmodifiableMap(builder.getMetaData());
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getHostName() {
        return hostName;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getArtifactVersion() {
        return artifactVersion;
    }

    public ProductIdEnum getProductId() {
        return productId;
    }

    public String getProductVersion() {
        return productVersion;
    }

    public Map<String, String> getMetaData() {
        return metaData;
    }

    public static class Builder {
        public static final String UNKNOWN_ID = "<unknown>";

        private String customerId;
        private String hostName;
        private String artifactId;
        private String artifactVersion;
        private ProductIdEnum productId;
        private String productVersion;
        private final Map<String, String> metaData = new HashMap<>();

        // PhoneHomeRequestBody only has a private constructor to force creation through the builder.
        @SuppressWarnings("synthetic-access")
        public PhoneHomeRequestBody build() throws IllegalStateException {
            validateRequiredParam(customerId, "customerId");
            validateRequiredParam(hostName, "hostName");
            validateRequiredParam(artifactId, "artifactId");
            validateRequiredParam(artifactVersion, "artifactVersion");
            if (productId == null) {
                throw new IllegalStateException("Required parameter 'productId' is not set");
            }
            validateRequiredParam(productVersion, "productVersion");
            return new PhoneHomeRequestBody(this);
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(final String customerId) {
            this.customerId = customerId;
        }

        public String getHostName() {
            return hostName;
        }

        public void setHostName(final String hostName) {
            this.hostName = hostName;
        }

        public String getArtifactId() {
            return artifactId;
        }

        public void setArtifactId(final String artifactId) {
            this.artifactId = artifactId;
        }

        public String getArtifactVersion() {
            return artifactVersion;
        }

        public void setArtifactVersion(final String artifactVersion) {
            this.artifactVersion = artifactVersion;
        }

        public ProductIdEnum getProductId() {
            return productId;
        }

        public void setProductId(final ProductIdEnum productId) {
            this.productId = productId;
        }

        public String getProductVersion() {
            return productVersion;
        }

        public void setProductVersion(final String productVersion) {
            this.productVersion = productVersion;
        }

        public Map<String, String> getMetaData() {
            return metaData;
        }

        public void addToMetaData(final String key, final String value) {
            metaData.put(key, value);
        }

        public String md5Hash(final String string) throws NoSuchAlgorithmException, UnsupportedEncodingException {
            final MessageDigest md = MessageDigest.getInstance(MessageDigestAlgorithms.MD5);
            final byte[] hashedBytes = md.digest(string.getBytes("UTF-8"));
            return DigestUtils.md5Hex(hashedBytes);
        }

        private void validateRequiredParam(final String param, final String paramName) throws IllegalStateException {
            if (StringUtils.isBlank(param)) {
                throw new IllegalStateException("Required parameter '" + paramName + "' is not set");
            }
        }

    }

}