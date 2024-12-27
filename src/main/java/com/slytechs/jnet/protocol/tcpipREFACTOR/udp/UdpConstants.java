/*
 * Sly Technologies Free License
 * 
 * Copyright 2024 Sly Technologies Inc.
 *
 * Licensed under the Sly Technologies Free License (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.slytechs.com/free-license-text
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.slytechs.jnet.protocol.tcpipREFACTOR.udp;

/**
 * 
 *
 * @author Mark Bednarczyk [mark@slytechs.com]
 * @author Sly Technologies Inc.
 */
public final class UdpConstants {

    public static final int UDP_HEADER_LEN = 8;
    public static final int UDP_FIELD_SRC_PORT = 0;
    public static final int UDP_FIELD_DST_PORT = 2;
    public static final int UDP_FIELD_LENGTH = 4;
    public static final int UDP_FIELD_CHECKSUM = 6;

    private UdpConstants() {
    }
}