/*
 * Copyright (c) 2021 Shadew
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.shadew.modutil.changelog;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.function.Supplier;

public class MarkdownChangelogGenerator implements ChangelogGenerator {
    private final Supplier<ChangelogInfo> info;
    private final File outFile;

    public MarkdownChangelogGenerator(Supplier<ChangelogInfo> info, File out) {
        this.info = info;
        this.outFile = out;
    }

    @Override
    public void generate() throws IOException {
        ChangelogInfo info = this.info.get();

        outFile.getParentFile().mkdirs();
        try (PrintStream out = new PrintStream(outFile)) {
            out.printf("## %s - %s\n", info.getVersionNumber(), info.getVersionName());
            out.println();
            out.printf("**For Minecraft %s**\n", info.getMcversion());
            out.println();
            if (info.getDescription() != null) {
                out.println(info.getDescription().replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replaceAll("\\r|\\n|\\r\\n", "  " + System.lineSeparator()));
                out.println();
            }
            out.println("#### Changelog");
            out.println();
            for (String changelog : info.getChangelog()) {
                out.printf("- %s\n", changelog.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replaceAll("\\r|\\n|\\r\\n", "  " + System.lineSeparator()));
            }
        }
    }
}
