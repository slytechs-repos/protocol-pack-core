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
package com.slytechs.jnet.protocol.core;

import static com.slytechs.jnet.protocol.core.constants.CoreConstants.*;

import com.slytechs.jnet.protocol.core.constants.CoreId;
import com.slytechs.jnet.protocol.core.constants.IcmpEchoMessageType;
import com.slytechs.jnet.protocol.meta.Meta;
import com.slytechs.jnet.protocol.meta.MetaResource;
import com.slytechs.jnet.protocol.meta.Meta.MetaType;

/**
 * The ICMP echo message version 6 header.
 * <p>
 * The ICMP echo message is a type of ICMP message that is used to test the
 * reachability of a network device. The ICMP echo message is also known as a
 * ping request.
 * </p>
 * <p>
 * The ICMP echo message has the following format:
 * </p>
 * <ul>
 * <li>Type: The type field is 8 bits long and specifies the type of ICMP
 * message. The type for an ICMP echo message is 8.</li>
 * <li>Code: The code field is 8 bits long and provides additional information
 * about the ICMP message. The code for an ICMP echo message is always 0.</li>
 * <li>Checksum: The checksum field is 16 bits long and is used to verify the
 * integrity of the ICMP message. The checksum is calculated by adding the
 * 16-bit values of all the fields in the ICMP header and the data field, and
 * then taking the one's complement of the result.</li>
 * <li>Identifier: The identifier field is 16 bits long and is used to identify
 * the ICMP echo message. The identifier is typically a random number that is
 * generated by the sender.</li>
 * <li>Sequence Number: The sequence number field is 16 bits long and is used to
 * sequence the ICMP echo messages. The sequence number is incremented for each
 * ICMP echo message that is sent.</li>
 * </ul>
 * <p>
 * The ICMP echo message is sent by the sender to the destination device. The
 * destination device responds with an ICMP echo reply message. The ICMP echo
 * reply message has the same format as the ICMP echo message, except that the
 * type field is 0.
 * </p>
 * <p>
 * The time it takes for the ICMP echo reply message to arrive is called the
 * round-trip time. The round-trip time can be used to measure the performance
 * of the network between the sender and the destination device.
 * </p>
 */
@MetaResource("icmp-echo-meta.json")
public sealed class Icmp6Echo
		extends Icmp6
		implements IcmpEchoMessage
		permits Icmp6Echo.Request, Icmp6Echo.Reply {

	/**
	 * ICMPv6 echo request header.
	 * <p>
	 * An ICMP echo request is a type of Internet Control Message Protocol (ICMP)
	 * packet that is used to test the reachability of a remote host. It is also
	 * known as a ping packet. When a host sends an ICMP echo request to another
	 * host, the receiving host sends an ICMP echo reply back to the sender. The
	 * time it takes for the echo reply to arrive is called the round-trip time
	 * (RTT).
	 * </p>
	 */
	public static final class Request extends Icmp6Echo {

		/** The Constant ID. */
		public static final int ID = CoreId.CORE_ID_ICMPv6_ECHO_REQUEST;

		/**
		 * Instantiates a new request.
		 */
		public Request() {
			super(ID);
		}
	}

	/**
	 * ICMPv6 echo reply header.
	 * <p>
	 * An ICMP echo reply is a type of Internet Control Message Protocol (ICMP)
	 * packet that is used to respond to an ICMP echo request. It is also known as a
	 * ping reply. When a host receives an ICMP echo request, it sends an ICMP echo
	 * reply back to the sender. The ICMP echo reply packet has the same format as
	 * the ICMP echo request packet, except that the type field is set to 0 (Echo
	 * Reply) and the code field is set to 0.
	 * </p>
	 */
	public static final class Reply extends Icmp6Echo {

		/** The Constant ID. */
		public static final int ID = CoreId.CORE_ID_ICMPv6_ECHO_REPLY;

		/**
		 * Instantiates a new reply.
		 */
		public Reply() {
			super(ID);
		}
	}

	/** The ICMP echo header ID constant. */
	public static final int ID = CoreId.CORE_ID_ICMPv6_ECHO;

	/**
	 * Instantiates a new ICMPv6 echo common header.
	 */
	public Icmp6Echo() {
		super(ID);
	}

	/**
	 * Instantiates a new ICMPv6 echo header with a subclass type.
	 *
	 * @param id the id
	 */
	protected Icmp6Echo(int id) {
		super(id);
	}

	/**
	 * Identifier.
	 *
	 * @return the int
	 */
	@Override
	@Meta
	public int identifier() {
		return Short.toUnsignedInt(buffer().getShort(ICMPv4_ECHO_FIELD_IDENTIFIER));
	}

	/**
	 * Sequence.
	 *
	 * @return the int
	 */
	@Override
	@Meta
	public int sequence() {
		return Short.toUnsignedInt(buffer().getShort(ICMPv4_ECHO_FIELD_SEQUENCE));
	}

	/**
	 * @see com.slytechs.jnet.protocol.core.IcmpEchoMessage#messageType()
	 */
	@Override
	@Meta(MetaType.ATTRIBUTE)
	public IcmpEchoMessageType messageType() {
		return isRequest() ? IcmpEchoMessageType.REQUEST : IcmpEchoMessageType.REPLY;
	}
}