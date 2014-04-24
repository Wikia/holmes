Holmes
======
Tool for identifying types of wikitext articles.

Example usage
---
build

holmes-classifier
===============
Implementation of classifier essentials: Extracting signals from wikitext, training/verification process and serialization/deserialization.

holmes-web
==========
Web endpoint for holmes classifier. Build process for holmes-web includes training classifier. To specify training set use training.set parameter:

Example:
```bash
mvn install -Dtraining.set=./training-data/2014.01.26.json
```

To deploy to pkg-s1:
cd holmes-web; mvn deploy -Dtraining.set=../training-data/2014.01.26.json

See holmes-maven-plugin for more information about training.

holmes-cli
==========
Command line tools for holmes.

wiki-tools
==========
Client tools for wiki api.
