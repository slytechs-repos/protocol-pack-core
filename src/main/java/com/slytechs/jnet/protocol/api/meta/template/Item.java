/*
 * Sly Technologies Free License
 * 
 * Copyright 2025 Sly Technologies Inc.
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

import java.util.Iterator;
import java.util.List;

/**
 * Used to for formatted display content such as groups that expand and provide
 * more extensive information information.
 *
 * @author Mark Bednarczyk [mark@slytechs.com]
 * @author Sly Technologies Inc.
 */
public sealed interface Item {

	record Items(List<Item> list) implements Iterable<Item> {

		/**
		 * @see java.lang.Iterable#iterator()
		 */
		@Override
		public Iterator<Item> iterator() {
			return list.iterator();
		}
	}

	record FieldItem(
			String name,
			TemplatePattern template,
			String label,
			Defaults defaults,
			Tags tags,
			Items items) implements Item {}

	public record HeaderItem(
			String name,
			TemplatePattern summary,
			boolean repeatable,
			Defaults defaults,
			Tags tags,
			Items items) implements Item {}

	record InfoItem(
			String name,
			TemplatePattern template,
			String label,
			Defaults defaults,
			Tags tags,
			Items items) implements Item {}

	String name();

	default String label() {
		return name();
	}

	default TemplatePattern template() {
		return summary();
	}

	default TemplatePattern summary() {
		return template();
	}

	Defaults defaults();

	Items items();
}