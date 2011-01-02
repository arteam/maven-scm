package org.apache.maven.scm.provider.git.gitexe.command.blame;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.maven.scm.command.blame.BlameLine;
import org.apache.maven.scm.log.ScmLogger;
import org.apache.maven.scm.util.AbstractConsumer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Evgeny Mandrikov
 * @author Olivier Lamy
 * @since 1.4
 */
public class GitBlameConsumer
    extends AbstractConsumer
{
    private static final String GIT_TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss Z";

    private List<BlameLine> lines = new ArrayList<BlameLine>();

    public GitBlameConsumer( ScmLogger logger )
    {
        super( logger );
    }

    public void consumeLine( String line )
    {
        String parts[] = line.split( "\t", 4 );
        String revision = parts[0];
        String author = parts[1].substring( 1 );
        String dateTimeStr = parts[2];

        Date dateTime = parseDate( dateTimeStr, null, GIT_TIMESTAMP_PATTERN );
        getLines().add( new BlameLine( dateTime, revision, author ) );

        if ( getLogger().isDebugEnabled() )
        {
            getLogger().debug( author + " " + dateTimeStr );
        }
    }

    public List<BlameLine> getLines()
    {
        return lines;
    }
}
