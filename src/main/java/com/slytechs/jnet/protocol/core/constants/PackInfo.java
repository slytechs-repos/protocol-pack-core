/*
 * Sly Technologies Free License
 * 
 * Copyright 2023 Sly Technologies Inc.
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
package com.slytechs.jnet.protocol.core.constants;

import static com.slytechs.jnet.protocol.HeaderId.*;

/**
 * Protocol pack definitions. A protocol pack contains a number of protocol ID
 * constants, which are defined in their constant tables i.e.
 * {@link CoreHeaderInfo}.
 * 
 * <p>
 * A protocol ID is a 32-bit value which is made up of 3 parts, where 1st part
 * is the 16-bit Pack/Index (or ID) of the protocol, and the remainder 16-bits
 * are optional:
 * </p>
 * <dl>
 * <dt>Protocol ID</dt>
 * <dd>(Mandatory)A 16-bit value uniquely identifying every protocol defined by
 * every protocol pack</dd>
 * <dt>Size</dt>
 * <dd>(Optional) The size of the protocol header, padded to a 32-bit value</dd>
 * <dt>Offset</dt>
 * <dd>(Optional) The offset of the protocol header value</dd>
 * </dl>
 * 
 * <p>
 * The values stored in the 32-bit integer are encoded according to the
 * following binary structure:
 * </p>
 * 
 * <pre>
 * struct ProtocolId_s{
 * 	uint32_t
 * 		index:8,  // Index within the protocol pack
 * 		pack:8,   // Protocol pack unique number
 * 		size:7,   // (Optional) Size of the protocol header (in units of 32-bits)
 * 		offset:9; // (Optional) Offset into the packet (in units of 8-bit bytes)
 * }
 * </pre>
 * 
 * <p>
 * The size and offset fields are optional, and can be encoded into the
 * primitive 32-bit variable at anytime, while still holding on to its protocol
 * identity. Constant tables will declare each protocol ID without the
 * offset/length fields, as those are not known until a packet is provided, but
 * with a single encoding of an integer primitive, the size/offset values can be
 * added with ease such as
 * {@code ProtocolPack.encode(CoreProtocolPack.IP4_ID, 14, 20)}.
 * </p>
 * 
 * @author Sly Technologies Inc
 * @author repos@slytechs.com
 * @author Mark Bednarczyk
 */
public enum PackInfo {

	/** The core. */
	CORE("core", PackInfo.CORE_MODULE, PackInfo.CORE_PACK),
	
	/** The opts. */
	OPTS("options"),
	
	/** The media. */
	MEDIA("media"),
	
	/** The web. */
	WEB("web", PackInfo.WEB_MODULE, PackInfo.WEB_PACK),
	
	/** The telco. */
	TELCO("telco"),
	
	/** The lte. */
	LTE("lte"),
	
	/** The db. */
	DB("database"),
	
	/** The ms. */
	MS("microsoft"),
	
	/** The aaa. */
	AAA("authentication"),

	;

	/** The Constant PACK_ID_CORE. */
	// @formatter:off
	public static final int PACK_ID_CORE      = 0  << PROTO_SHIFT_PACK;
	
	/** The Constant PACK_ID_OPTIONS. */
	public static final int PACK_ID_OPTIONS   = 1  << PROTO_SHIFT_PACK;
	
	/** The Constant PACK_ID_MEDIA. */
	public static final int PACK_ID_MEDIA     = 2  << PROTO_SHIFT_PACK;
	
	/** The Constant PACK_ID_WEB. */
	public static final int PACK_ID_WEB       = 3  << PROTO_SHIFT_PACK;
	
	/** The Constant PACK_ID_TELCO. */
	public static final int PACK_ID_TELCO     = 4  << PROTO_SHIFT_PACK;
	
	/** The Constant PACK_ID_LTE. */
	public static final int PACK_ID_LTE       = 5  << PROTO_SHIFT_PACK;
	
	/** The Constant PACK_ID_DB. */
	public static final int PACK_ID_DB        = 6  << PROTO_SHIFT_PACK;
	
	/** The Constant PACK_ID_MS. */
	public static final int PACK_ID_MS        = 7  << PROTO_SHIFT_PACK;
	
	/** The Constant PACK_ID_AAA. */
	public static final int PACK_ID_AAA       = 8  << PROTO_SHIFT_PACK;
	// @formatter:on

	/** The Constant CORE_MODULE. */
	// @formatter:off
	private static final String CORE_MODULE = "com.slytechs.jnet.protocol.core";
	
	/** The Constant CORE_PACK. */
	private static final String CORE_PACK   = "com.slytechs.jnet.protocol.core.CorePack";
	
	/** The Constant WEB_MODULE. */
	private static final String WEB_MODULE = "com.slytechs.jnet.protocol.web";
	
	/** The Constant WEB_PACK. */
	private static final String WEB_PACK   = "com.slytechs.jnet.protocol.web.WebPack";
	// @formatter:on

	/** The id. */
	private final int id;
	
	/** The module name. */
	private final String moduleName;
	
	/** The class name. */
	private final String className;
	
	/** The name. */
	private final String name;

	/**
	 * Instantiates a new pack info.
	 *
	 * @param name the name
	 */
	PackInfo(String name) {
		this.name = name;
		this.className = null;
		this.moduleName = null;
		this.id = (ordinal() << PROTO_SHIFT_PACK);
	}

	/**
	 * Instantiates a new pack info.
	 *
	 * @param name       TODO
	 * @param moduleName TODO
	 * @param className  the class name
	 */
	PackInfo(String name, String moduleName, String className) {
		this.name = name;
		this.moduleName = moduleName;
		this.className = className;
		this.id = (ordinal() << PROTO_SHIFT_PACK);
	}

	/**
	 * Value of pack id.
	 *
	 * @param id the id
	 * @return the pack info
	 */
	public static PackInfo valueOfPackId(int id) {
		return values()[decodePackOrdinal(id)];
	}

	/**
	 * Gets the pack id as int.
	 *
	 * @return the pack id as int
	 */
	public int getPackIdAsInt() {
		return id;
	}

	/**
	 * Gets the pack class name.
	 *
	 * @return the pack class name
	 */
	public String getPackClassName() {
		return className;
	}

	/**
	 * Gets the pack module name.
	 *
	 * @return the pack module name
	 */
	public String getPackModuleName() {
		return moduleName;
	}

	/**
	 * Gets the pack name.
	 *
	 * @return the pack name
	 */
	public String getPackName() {
		return name;
	}
}