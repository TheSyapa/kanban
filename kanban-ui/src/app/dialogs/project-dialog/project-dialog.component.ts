import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ProjectService} from '../../service/kanban-service.service';
import {ProjectUpdateService} from '../../service/project-update.service';

@Component({
  selector: 'app-project-dialog',
  templateUrl: './project-dialog.component.html',
  styleUrls: ['./project-dialog.component.css']
})
export class ProjectDialogComponent implements OnInit {

  name: string;
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<ProjectDialogComponent>,
    @Inject(MAT_DIALOG_DATA) data,
    private projectUpdateService: ProjectUpdateService,
    private projectService: ProjectService) {

    this.form = fb.group({
      name: ['', Validators.required]
    });
  }

  ngOnInit() {
    this.projectUpdateService.updateList();
  }

  close() {
    this.dialogRef.close();
  }

  save() {
    this.name = this.form.get('name').value;
    if (this.name) {
      this.projectService.saveNewProject(this.name).subscribe(
        response => {
          this.dialogRef.close();
          this.projectUpdateService.updateList();
        },
        error => {
          console.error(error);
          this.dialogRef.close();
        }
      );
    }
  }

}
