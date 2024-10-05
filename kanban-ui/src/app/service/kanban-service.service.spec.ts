import { TestBed } from '@angular/core/testing';

import { ProjectService } from './kanban-service.service';

describe('KanbanServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ProjectService = TestBed.get(ProjectService);
    expect(service).toBeTruthy();
  });
});
