CREATE TABLE assignedMembers(
    MEMBERSid INTEGER NOT NULL,
    PROJECTSid INTEGER NOT NULL,
    PRIMARY KEY (membersid, projectsid),
    FOREIGN KEY (membersid) references members (id),
    FOREIGN KEY (projectsid) references projects (id)
)