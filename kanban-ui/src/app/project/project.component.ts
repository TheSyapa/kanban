import {Component, OnInit} from '@angular/core';
import {ProjectService} from '../service/kanban-service.service';
import {ActivatedRoute} from '@angular/router';
import {Project} from '../model/project/project';
import {Card} from '../model/card/card';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import {MatDialog, MatDialogConfig} from '@angular/material';
import {CardDialogComponent} from '../dialogs/card-dialog/card-dialog.component';
import {CardService} from '../service/card.service';

@Component({
  selector: 'app-kanban',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css']
})
export class ProjectComponent implements OnInit {

  constructor(
    private projectService: ProjectService,
    private cardService: CardService,
    private route: ActivatedRoute,
    private dialog: MatDialog
  ) {
    this.project = new Project();
  }

  project: Project;
  lists: { id: string, name: string, data: Card[], connectedTo: string[] }[];

  ngOnInit() {
    this.getProject();
    this.lists = [
      {
        id: 'CREATED',
        name: 'Новые',
        data: [],
        connectedTo: ['TODO', 'INFO', 'CANCEL']
      },
      {
        id: 'TODO',
        name: 'Готовы к работе',
        data: [],
        connectedTo: ['INFO', 'INPROGRESS', 'CANCEL']
      },
      {
        id: 'INFO',
        name: 'Требуется информация',
        data: [],
        connectedTo: ['TODO', 'INPROGRESS', 'CANCEL']
      },
      {
        id: 'INPROGRESS',
        name: 'В работе',
        data: [],
        connectedTo: ['TODO', 'INFO', 'REVIEW', 'CANCEL']
      },
      {
        id: 'REVIEW',
        name: 'Ревью',
        data: [],
        connectedTo: ['INFO', 'INPROGRESS', 'DONE', 'CANCEL']
      },
      {
        id: 'DONE',
        name: 'Выполнено',
        data: [],
        connectedTo: ['INFO', 'INPROGRESS', 'REVIEW']
      },
      {
        id: 'CANCEL',
        name: 'Отклонено',
        data: [],
        connectedTo: ['CREATED', 'TODO', 'INFO', 'INPROGRESS', 'REVIEW']
      },
    ];
  }

  drop(event: CdkDragDrop<string[]>) {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      this.updateCardStatusAfterDragDrop(event);
      transferArrayItem(event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex);
    }
  }

  openDialogForNewCard(): void {
    this.openDialog('Создание новой карточки', new Card());
  }

  openCardDialog(card: Card): void {
    this.cardService.getCardById(card.id.toString()).subscribe(
      response => {
        this.openDialog('Просмотреть/Изменить', response);
      }
    );
  }

  private getProject(): void {
    const id = this.route.snapshot.paramMap.get('id');

    this.projectService.retrieveProjectById(id).subscribe(
      response => {
        this.project = response;
        this.splitCardsByStatus(response);
      }
    );
  }

  private splitCardsByStatus(project: Project): void {
    this.lists[0].data = project.cards.filter(t => t.status === 'CREATED');
    this.lists[1].data = project.cards.filter(t => t.status === 'TODO');
    this.lists[2].data = project.cards.filter(t => t.status === 'INFO');
    this.lists[3].data = project.cards.filter(t => t.status === 'INPROGRESS');
    this.lists[4].data = project.cards.filter(t => t.status === 'REVIEW');
    this.lists[5].data = project.cards.filter(t => t.status === 'DONE');
    this.lists[6].data = project.cards.filter(t => t.status === 'CANCEL');
  }

  private updateCardStatusAfterDragDrop(event: CdkDragDrop<string[], string[]>) {
    this.cardService.getCardById(event.item.element.nativeElement.id).subscribe(
      response => {
        this.updateCardStatus(response, event.container.id);
      }
    );
  }

  private updateCardStatus(card: Card, containerId: string): void {
    if (containerId === 'CREATED') {
      card.status = 'CREATED';
    } else if (containerId === 'TODO') {
      card.status = 'TODO';
    } else if (containerId === 'INFO') {
      card.status = 'INFO';
    } else if (containerId === 'INPROGRESS') {
      card.status = 'INPROGRESS';
    } else if (containerId === 'REVIEW') {
      card.status = 'REVIEW';
    } else if (containerId === 'DONE') {
      card.status = 'DONE';
    } else if (containerId === 'CANCEL') {
      card.status = 'CANCEL';
    }
    this.cardService.updateCard(card).subscribe();
  }

  private openDialog(name: string, card: Card): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      dialogName: name,
      card,
      projectId: this.project.id
    };
    const dialogRef = this.dialog.open(CardDialogComponent, dialogConfig);
    dialogRef.afterClosed().subscribe((response: Card) => {
      if (response) {
        this.getProject();
      }
    });
  }
}
