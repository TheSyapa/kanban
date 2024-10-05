import {Component, Inject, NgModule, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {Card} from '../../model/card/card';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {ProjectService} from '../../service/kanban-service.service';
import {CardService} from '../../service/card.service';

@Component({
  selector: 'app-card-dialog',
  templateUrl: './card-dialog.component.html',
  styleUrls: ['./card-dialog.component.css']
})
export class CardDialogComponent implements OnInit {

  dialogName: string;
  projectId: string;
  card: Card;
  form: FormGroup;


  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<CardDialogComponent>,
    @Inject(MAT_DIALOG_DATA) data,
    private projectService: ProjectService,
    private cardService: CardService) {

    this.dialogName = data.dialogName;
    this.projectId = data.projectId;
    this.card = data.card;

    this.form = fb.group({
      name: [this.card.name, Validators.required],
      description: [this.card.description, Validators.required],
      color: [this.card.color, Validators.required]
    });
  }

  ngOnInit() {
    this.form = new FormGroup({
      name: new FormControl(''),
      type: new FormControl(''),
      author: new FormControl(''),
      worker: new FormControl(''),
      tester: new FormControl(''),
      timeCreated: new FormControl(''),
      timeTaken: new FormControl(''),
      timeDeadline: new FormControl(''),
      timeFactEndTime: new FormControl(''),
      status: new FormControl(''),
      direction: new FormControl(''),
      color: new FormControl(''),
      description: new FormControl(''),
    });
  }

  save(): void {
    this.mapFormToTaskModel();
    if (!this.card.id) {
      this.card.status = 'CREATED';
      this.card.timeCreated = new Date().toISOString();
      this.projectService.saveNewCardInProject(this.projectId, this.card).subscribe();
    } else {
      this.cardService.updateCard(this.card).subscribe();
    }
    this.dialogRef.close(this.card);
  }

  close() {
    this.dialogRef.close();
  }

  private mapFormToTaskModel(): void {
    this.card.name = this.form.get('name').value;
    this.card.type = this.form.get('type').value;
    this.card.author = this.form.get('author').value;
    this.card.worker = this.form.get('worker').value;
    this.card.tester = this.form.get('tester').value;
    this.card.timeCreated = this.form.get('timeCreated').value;
    this.card.timeTaken = this.form.get('timeTaken').value;
    this.card.timeDeadline = this.form.get('timeDeadline').value;
    this.card.timeFactEndTime = this.form.get('timeFactEndTime').value;
    this.card.status = this.form.get('status').value;
    this.card.direction = this.form.get('direction').value;
    this.card.color = this.form.get('color').value;
    this.card.description = this.form.get('description').value;
  }
}
