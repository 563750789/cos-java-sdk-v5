/*
 * Copyright 2010-2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 
 * According to cos feature, we modify some class，comment, field name, etc.
 */


package com.qcloud.cos.internal.crypto;

import com.qcloud.cos.auth.COSCredentialsProvider;
import com.qcloud.cos.internal.COSDirect;

public class COSCryptoModuleAECbc extends COSCryptoModuleAE {

    public COSCryptoModuleAECbc(COSDirect cos, COSCredentialsProvider credentialsProvider,
            EncryptionMaterialsProvider kekMaterialsProvider, CryptoConfiguration cryptoConfig) {
        this(null, cos, credentialsProvider, kekMaterialsProvider, cryptoConfig);
    }

    public COSCryptoModuleAECbc(QCLOUDKMS kms, COSDirect cos,
            COSCredentialsProvider credentialsProvider,
            EncryptionMaterialsProvider kekMaterialsProvider, CryptoConfiguration cryptoConfig) {
        super(kms, cos, credentialsProvider, kekMaterialsProvider, cryptoConfig);
    }

    protected long ciphertextLength(long originalContentLength) {
        return originalContentLength + 16 - originalContentLength % 16;
    }
}