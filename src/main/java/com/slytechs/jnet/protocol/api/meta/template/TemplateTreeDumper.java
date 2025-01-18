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
package com.slytechs.jnet.protocol.api.meta.template;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import com.slytechs.jnet.platform.api.util.format.Detail;
import com.slytechs.jnet.platform.api.util.format.Indentation;
import com.slytechs.jnet.platform.api.util.format.IndentationStack;
import com.slytechs.jnet.protocol.api.meta.template.Item.FieldItem;
import com.slytechs.jnet.protocol.api.meta.template.Template.HeaderTemplate;
import com.slytechs.jnet.protocol.api.meta.template.Template.HeaderTemplate.Details.HeaderDetail;

/**
 * 
 *
 * @author Mark Bednarczyk [mark@slytechs.com]
 * @author Sly Technologies Inc.
 */
public final class TemplateTreeDumper {

	private interface Fmts {

		/**
		 * Double-quote string.
		 *
		 * @param str the str
		 * @return the string
		 */
		static String dq(String str) {
			StringBuilder sb = new StringBuilder();
			sb.append('\"')
					.append(str)
					.append('\"');

			return sb.toString();
		}
	}

	/**
	 * 
	 */
	private static final int INDENT = 2;
	private static final char NEW_LINE = '\n';
	private final Appendable out;

	private final Detail printerDetail;

	private final Indentation stack = new IndentationStack();

	public TemplateTreeDumper() {
		this(System.out, Detail.DEFAULT);
	}

	public TemplateTreeDumper(Appendable out, Detail detail) {
		this.printerDetail = detail;
		this.out = Objects.requireNonNull(out);
	}

	public TemplateTreeDumper(Detail detail) {
		this(System.out, detail);
	}

	private void branch(Object obj) throws IOException {

		stack.push(INDENT);
		try {
			switch (obj) {
			case HeaderTemplate p -> printProtocolTemplate(p);
			case HeaderDetail d -> printDetailTemplate(d);
			case FieldItem f -> printField(f);
			case Item i -> printItem(i);
			case List<?> l -> l.forEach(this::printe);

			default -> println("- %s", Objects.toIdentityString(obj));
			}
		} finally

		{
			stack.pop();
		}
	}

	private void leaf(Object obj) throws IOException {
		switch (obj) {

		default -> println(Objects.toIdentityString(obj));
		}
	}

	public void print(Object obj) throws IOException {
		print(obj, "");
	}

	public void print(Object obj, String comment) throws IOException {
		if (obj == null) {
			println("<NULL> #" + comment);
			return;
		}

		switch (obj) {
		case String str -> println(str);
		case HeaderTemplate p -> branch(p);
		case HeaderDetail d -> branch(d);
		case FieldItem d -> branch(d);
		case Defaults d -> printDefaults(d);
		case Macros m -> printMacros(m);
		case Item i -> branch(i);
		case List<?> l -> branch(l);

		default -> leaf(obj);
		}
	}

	private void printDefaults(Defaults defaults) throws IOException {
		if (defaults == null)
			return;

		println("defaults: " + defaults.toString());

		if (true)
			return;

		stack.pushAndPrint(out, "- ");
		try {
			println(defaults.toString());
//			println("indent: %s", defaults.indent());
//			println("width: %s", defaults.width());
//			println("align: %s", defaults.align());
//			println("prefix: %s", Fmts.dq(defaults.prefix()));
		} finally {
			stack.pop();
		}

	}

	private void printDetailTemplate(HeaderDetail detail) throws IOException {
		stack.pushAndPrint(out, "- ");
		try {
			if (detail == null) {
				print(null, "detail");
				return;
			}

			println("detail: %s", detail.detail());
			println("summary: %s", detail.summary());
			println("items");
			print(detail.items().list(), "items");
		} finally {
			stack.pop();
		}
	}

	public void printe(Object obj) {
		try {
			print(obj, "");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void printField(FieldItem field) throws IOException {
		if (field == null)
			return;

		stack.pushAndPrint(out, "- ");
		try {
			println("name: %s", field.name());
			println("label: %s", field.label());

			if (field.template() != null)
				println("template: %s", Fmts.dq(field.template().toString()));

			println("items:");
			branch(field.items());
		} finally {
			stack.pop();
		}

	}

	private void printItem(Item item) throws IOException {
		if (item == null)
			return;

		stack.pushAndPrint(out, "- ");
		try {
			println("name: %s", item.name());
			if (item.template() != null)
				println("template: %s", Fmts.dq(item.template().toString()));

			print(item.defaults());

			branch(item.items());
		} finally {
			stack.pop();
		}

	}

	private void println(String fmt, Object... args) throws IOException {
		stack.indent(out);

		out.append(args.length == 0 ? fmt : fmt.formatted(args));
		out.append(NEW_LINE);
		stack.clearDirty();;
	}

	private void printMacros(Macros macros) throws IOException {
		if (macros == null)
			return;

		println("macros: " + macros.macroMap().size() + " entries");
	}

	private void printProtocolTemplate(HeaderTemplate header) throws IOException {
		for (Detail detail : Detail.values()) {

			stack.pushAndPrint(out, "- ");
			try {
				print(header.macros());

				println("details:");

				HeaderDetail d = header.templateDetail(detail);
				print(d, detail.toString());
			} finally {
				stack.pop();
			}
		}
	}

}