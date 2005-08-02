package org.apache.maven.scm.provider.perforce.command.changelog;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.File;
import java.util.ArrayList;

import org.apache.maven.scm.ScmTestCase;
import org.apache.maven.scm.ChangeSet;

/**
 * @author <a href="mailto:evenisse@apache.org">Emmanuel Venisse</a>
 * @version $Id$
 */
public class PerforceChangeLogConsumerTest
    extends ScmTestCase
{
    public void testParse()
        throws Exception
    {
        File testFile = getTestFile( "src/test/resources/perforce/perforcelog.txt" );

        PerforceChangeLogConsumer consumer = new PerforceChangeLogConsumer( null, null );

        FileInputStream fis = new FileInputStream( testFile );
        BufferedReader in = new BufferedReader( new InputStreamReader( fis ) );
        String s = in.readLine();
        while ( s != null )
        {
            consumer.consumeLine( s );
            s = in.readLine();
        }

        ArrayList entries = new ArrayList( consumer.getModifications() );
        assertEquals( "Wrong number of entries returned", 9, entries.size() );
        ChangeSet entry = (ChangeSet) entries.get( 0 );
        assertEquals( "jim", entry.getAuthor() );
        assertEquals( "//depot/test/demo/demo.c", entry.getFile().getName() );
        assertEquals( "2003-10-01", entry.getDateFormatted() );
        assertEquals( "16:24:20", entry.getTimeFormatted() );
    }
}