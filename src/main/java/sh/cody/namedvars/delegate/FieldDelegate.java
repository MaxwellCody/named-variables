/*
 * Copyright (c) 2020 - Maxwell Cody <maxwell@cody.sh>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package sh.cody.namedvars.delegate;

import java.lang.reflect.Field;

public class FieldDelegate<T> implements Delegate<T> {
   private final Object instance;
   private final Field field;

   public FieldDelegate(Object instance, Field field) {
      this.instance = instance;
      this.field = field;
   }

   /*
    * The readField() & writeField() methods are separate from the get() & set() methods for the convenience of
    * descendant classes.
    */

   protected final void writeField(Object value) {
      try {
         this.field.setAccessible(true);
         this.field.set(this.instance, value);
      } catch(IllegalAccessException exception) {
         throw new RuntimeException("Failed to write to reflected field.", exception);
      }
   }

   protected final Object readField() {
      try {
         this.field.setAccessible(true);
         return this.field.get(this.instance);
      } catch(IllegalAccessException exception) {
         throw new RuntimeException("Failed to read from reflected field.", exception);
      }
   }

   @SuppressWarnings("unchecked")
   @Override
   public T get() {
      return (T) this.readField();
   }

   @Override
   public void set(T value) {
      this.writeField(value);
   }
}