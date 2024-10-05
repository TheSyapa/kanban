import {Component, OnInit} from '@angular/core';
import {Project} from '../model/project/project';
import {ProjectService} from '../service/kanban-service.service';
import {MatDialog, MatDialogConfig} from '@angular/material';
import {ProjectDialogComponent} from '../dialogs/project-dialog/project-dialog.component';
import {ProjectUpdateService} from '../service/project-update.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  projects: Project[];


  constructor(
    private projectUpdateService: ProjectUpdateService,
    private projectService: ProjectService,
    private dialog: MatDialog
  ) {
  }

  ngOnInit() {
    this.updateList();
    this.projectUpdateService.updateProjectList$.subscribe(() => {
      this.updateList();
    });
  }

  updateList(): void {
    this.retrieveAllProject();
  }

  openDialogForNewProject(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      kanban: new Project()
    };
    this.dialog.open(ProjectDialogComponent, dialogConfig);
  }

  retrieveAllProject(): void {
    this.projectService.retrieveAllProjectBoards().subscribe(
      response => {
        this.projects = response;
      }
    );
  }
}
